package avetmiss.controller;

import avetmiss.AvetmissExportService;
import avetmiss.AvetmissInputFileApplicationService;
import avetmiss.AvetmissNatGenerationApplicationService;
import avetmiss.client.Endpoint;
import avetmiss.client.ClarityShareServiceClient;
import avetmiss.client.http.HttpClaritySharedService;
import avetmiss.controller.payload.inputFile.NatGenerationRequest;
import avetmiss.controller.payload.nat.NatFileReadModel;
import avetmiss.controller.payload.nat.NatFilesRequest;
import avetmiss.export.InputReader2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;

@RestController
public class AvetmissNatFileGenerateController {
    private static Logger logger = LoggerFactory.getLogger(AvetmissNatFileGenerateController.class);

    @Autowired
    private AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService;

    @Autowired
    private AvetmissInputFileApplicationService avetmissInputFileApplicationService;

    private AvetmissExportService avetmissExportService(ClarityShareServiceClient clarityShareServiceClient) {
        return new AvetmissExportService(
                avetmissInputReader(clarityShareServiceClient),
                clarityShareServiceClient,
                avetmissInputFileApplicationService,
                avetmissNatGenerationApplicationService);
    }

    private InputReader2 avetmissInputReader(ClarityShareServiceClient clarityShareServiceClient) {
        return new InputReader2(clarityShareServiceClient);
    }

    @PostMapping("/generate-nat")
    ResponseEntity generateNatFiles(@RequestBody NatGenerationRequest natFilesRequest) {
        File inputFile = new File(natFilesRequest.inputFilePath);
        if(!inputFile.exists()) {
            return ResponseEntity.badRequest().body("Input file '" + natFilesRequest.inputFilePath + "' doesn't exist");
        }

        try {
            Endpoint endpoint = Endpoint.fromString(natFilesRequest.deployment);

            ClarityShareServiceClient clarityShareServiceClient = new HttpClaritySharedService(new RestTemplate(), endpoint.baseUrl());

            String result =
                    avetmissExportService(clarityShareServiceClient).process(inputFile, new File(natFilesRequest.outputZipFilePath));

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("fail to generate nat.zip", e);
            return ResponseEntity.badRequest().body(ExceptionUtils.getFullStackTrace(e));
        }
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
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
