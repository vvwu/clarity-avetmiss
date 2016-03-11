package avetmiss.controller;

import avetmiss.UnitImportApplicationService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/importUnits")
public class ImportNtisUnitsController {

    @Autowired
    private UnitImportApplicationService unitImportApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<String> importNtisUnitFile(@RequestBody String csvContent) {
        try {
            String result = unitImportApplicationService.importNtisUnits(csvContent);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
