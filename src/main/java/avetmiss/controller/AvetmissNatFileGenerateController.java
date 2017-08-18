package avetmiss.controller;

import avetmiss.AvetmissExportService;
import avetmiss.AvetmissNatGenerationApplicationService;
import avetmiss.client.ClarityShareServiceClient;
import avetmiss.client.Endpoint;
import avetmiss.client.http.HttpClaritySharedService;
import avetmiss.controller.payload.inputFile.NatGenerationRequest;
import avetmiss.controller.payload.nat.NatFileReadModel;
import avetmiss.controller.payload.nat.NatFilesRequest;
import avetmiss.controller.payload.nat.NatResultReadModel;
import avetmiss.util.Util;
import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@RestController
public class AvetmissNatFileGenerateController {
    private static Logger logger = LoggerFactory.getLogger(AvetmissNatFileGenerateController.class);

    @Autowired
    private AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService;

    @Autowired
    private AvetmissExportService avetmissExportService;

    @Value("${nat.output.dir}")
    private String natOutputDir;

    @PostMapping("/generate-nat")
    public ResponseEntity  generateNatFiles(@RequestBody NatGenerationRequest natFilesRequest) {
        File inputFile = new File(natFilesRequest.inputFilePath);
        if(!inputFile.exists()) {
            return ResponseEntity.badRequest().body("Input file '" + natFilesRequest.inputFilePath + "' doesn't exist");
        }

        Endpoint endpoint = Endpoint.fromString(natFilesRequest.deployment);

        File natParentDirForCurrentDeployment = new File(natOutputDir, endpoint.name());

        int maxNumericDirectory = Util.maxNumericDirectory(natParentDirForCurrentDeployment);

        File nextNatOutputDir = new File(natParentDirForCurrentDeployment, (maxNumericDirectory + 1) + "");


        ClarityShareServiceClient clarityShareServiceClient = new HttpClaritySharedService(new RestTemplate(), endpoint.baseUrl());

        avetmissExportService.generateNatFile(clarityShareServiceClient, inputFile, nextNatOutputDir);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/nat-results/{deployment}")
    ResponseEntity<List<NatResultReadModel>> natResults(@PathVariable(value = "deployment") String deployment) {
        Endpoint endpoint = Endpoint.fromString(deployment);

        File natParentDirForCurrentDeployment = new File(natOutputDir, endpoint.name());

        File[] natResultDirs = Util.numericDirectories(natParentDirForCurrentDeployment);

        List<NatResultReadModel> results =
                Stream.of(natResultDirs)
                        .map(dir -> {
                            NatResultReadModel resultReadModel = new NatResultReadModel();

                            resultReadModel.number = Integer.parseInt(dir.getName());

                            File[] zipFiles = dir.listFiles(pathname -> pathname.getName().endsWith(".zip"));
                            if(zipFiles != null && zipFiles.length > 0) {
                                resultReadModel.natFilePath = zipFiles[0].getAbsolutePath();
                            }

                            File resultFile = new File(dir, "result.txt");
                            if(resultFile.exists()) {
                                try {
                                    resultReadModel.result = Files.toString(resultFile, Charset.defaultCharset());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            return resultReadModel;
                        }).collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @PostMapping("/nat.zip")
    ResponseEntity getNats(@RequestBody NatFilesRequest natFilesRequest) throws IOException {
        try {
            byte[] bytes = avetmissNatGenerationApplicationService.getNatFileZip(natFilesRequest);

            try (FileWriter fileWriter
                         = new FileWriter(new File(format("nat-%s.zip", new LocalDateTime().toString("yyyyMMddHHmmss"))))) {
                IOUtils.write(bytes, fileWriter);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return new ResponseEntity<>(bytes,
                    injectFileNameIntoHeaders("nat.zip"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("fail to generate nat.zip", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/natFiles")
    NatFileReadModel getNatFiles(@RequestBody NatFilesRequest natFilesRequest) {
        return avetmissNatGenerationApplicationService.getNatFiles(natFilesRequest);
    }

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private HttpHeaders injectFileNameIntoHeaders(String fileName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return responseHeaders;
    }
}
