package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.controller.payload.SuburbReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuburbController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @GetMapping("/suburbs/{postcode}")
    List<SuburbReadModel> suburb(@PathVariable String postcode) {
        return avetmissApplicationService.findSuburbsByPostCode(postcode);
    }

}
