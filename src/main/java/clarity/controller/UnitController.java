package clarity.controller;

import clarity.AvetmissApplicationService;
import clarity.controller.payload.UnitReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class UnitController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

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

}
