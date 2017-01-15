package avetmiss.controller;

import avetmiss.AvetmissNatGenerationApplicationService;
import avetmiss.controller.payload.nat.NatFileReadModel;
import avetmiss.controller.payload.nat.NatFilesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AvetmissNatFileGenerateController {

    @Autowired
    private AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService;

    @PostMapping("/nat.zip")
    ResponseEntity getNats(@RequestBody NatFilesRequest natFilesRequest) throws IOException {
        try {
            byte[] bytes = avetmissNatGenerationApplicationService.getNatFileZip(natFilesRequest);

            return new ResponseEntity<byte[]>(bytes,
                    injectFileNameIntoHeaders("nat.zip"), HttpStatus.OK);
        } catch (Exception e) {
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
