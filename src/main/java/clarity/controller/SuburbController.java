package clarity.controller;

import clarity.AvetmissApplicationService;
import clarity.controller.payload.SuburbReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class SuburbController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @RequestMapping(method = RequestMethod.GET, value = "/suburbs/{postcode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<SuburbReadModel>> suburb(@PathVariable String postcode) {
        List<SuburbReadModel> suburbs = avetmissApplicationService.findSuburbsByPostCode(postcode);

        return ResponseEntity.ok(suburbs);
    }

}
