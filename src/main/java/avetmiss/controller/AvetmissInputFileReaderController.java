package avetmiss.controller;

import avetmiss.AvetmissInputFileApplicationService;
import avetmiss.controller.payload.inputFile.AvetmissInputFileProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readInput")
public class AvetmissInputFileReaderController {

    @Autowired
    private AvetmissInputFileApplicationService avetmissInputFileApplicationService;

    @PostMapping
    AvetmissInputFileProcessResult processCsv(@RequestBody String csvContent) {
        AvetmissInputFileProcessResult result = avetmissInputFileApplicationService.readAndValidate(csvContent);
        return result;
    }
}
