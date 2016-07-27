package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.UnitImportApplicationService;
import avetmiss.controller.payload.UnitCreateRequest;
import avetmiss.controller.payload.UnitReadModel;
import avetmiss.exception.UnitExistException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class UnitController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @Autowired
    private UnitImportApplicationService unitImportApplicationService;

    @RequestMapping(method = RequestMethod.GET, value = "/units/{unitCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<UnitReadModel> unit(@PathVariable String unitCode) {
        UnitReadModel unit = avetmissApplicationService.findUnitByCode(unitCode);
        if (unit == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(unit);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/units/importUnits")
    ResponseEntity<String> importNtisUnitFile(@RequestBody String csvContent) {
        try {
            String result = unitImportApplicationService.importNtisUnits(csvContent);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionUtils.getFullStackTrace(e));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/units",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createUnit(@RequestBody UnitCreateRequest unitCreateRequest) {
        try {
            unitImportApplicationService.createUnit(unitCreateRequest);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
