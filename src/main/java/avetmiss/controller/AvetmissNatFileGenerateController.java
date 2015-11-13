package avetmiss.controller;

import avetmiss.AvetmissNatGenerationApplicationService;
import avetmiss.controller.payload.nat.NatFileReadModel;
import avetmiss.controller.payload.nat.NatFilesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AvetmissNatFileGenerateController {

    @Autowired
    private AvetmissNatGenerationApplicationService avetmissNatGenerationApplicationService;

    @RequestMapping(value = "nat.zip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> getNats(@RequestBody NatFilesRequest natFilesRequest) throws IOException {
        byte[] bytes = avetmissNatGenerationApplicationService.getNatFileZip(natFilesRequest);

        return new ResponseEntity<byte[]>(bytes,
                injectFileNameIntoHeaders("nat.zip"), HttpStatus.OK);
    }

    @RequestMapping(value = "natFiles", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    NatFileReadModel getNatFiles(@RequestBody NatFilesRequest natFilesRequest) {
        NatFileReadModel natFileReadModel = avetmissNatGenerationApplicationService.getNatFiles(natFilesRequest);
        return natFileReadModel;
    }

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private HttpHeaders injectFileNameIntoHeaders(String fileName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return responseHeaders;
    }
}
