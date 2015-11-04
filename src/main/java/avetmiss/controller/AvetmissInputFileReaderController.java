package avetmiss.controller;

import avetmiss.AvetmissInputFileApplicationService;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/readInput")
public class AvetmissInputFileReaderController {

    @Autowired
    private AvetmissInputFileApplicationService avetmissInputFileApplicationService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    AvetmissInputFileProcessResult processCsv(@RequestBody String csvContent) {
        AvetmissInputFileProcessResult result = avetmissInputFileApplicationService.readAndValidate(csvContent);
        return result;
    }
}
