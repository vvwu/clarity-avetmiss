package avetmiss.controller;

import avetmiss.UnitImportApplicationService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ImportUnitController {

    @PostMapping("/units/importUnits")
    ResponseEntity<String> importNtisUnitFile(@RequestBody String txtContent) {
        try {
            String result = unitImportApplicationService.importNtisUnitsFromTxt(txtContent);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionUtils.getFullStackTrace(e));
        }
    }

    @PostMapping("/units/importUnits/csv")
    ResponseEntity<String> importNtisUnitFileCsv(@RequestBody String csvContent) {
        try {
            String result = unitImportApplicationService.importNtisUnitsFromCsv(csvContent);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionUtils.getFullStackTrace(e));
        }
    }


    @Autowired
    private UnitImportApplicationService unitImportApplicationService;
}
