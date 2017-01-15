package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.UnitImportApplicationService;
import avetmiss.controller.payload.UnitCreateRequest;
import avetmiss.controller.payload.UnitReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/units")
public class UnitController {

    @PostMapping
    ResponseEntity createUnit(@RequestBody UnitCreateRequest unitCreateRequest) {
        try {
            unitImportApplicationService.createUnit(unitCreateRequest);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{unitCode}")
    ResponseEntity<UnitReadModel> getUnit(@PathVariable String unitCode) {
        UnitReadModel unit = avetmissApplicationService.findUnitByCode(unitCode);
        if (unit == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(unit);
    }

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @Autowired
    private UnitImportApplicationService unitImportApplicationService;
}
