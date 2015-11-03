package clarity.controller;

import java.util.List;

import clarity.AvetmissApplicationService;
import clarity.controller.payload.LabelValueReadModel;
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
@RequestMapping(value = "/identifiers")
@EnableAutoConfiguration
public class AvetmissIdentifiersController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @RequestMapping(method = RequestMethod.GET, value = "/concessionType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getConcessionTypeIdentifiers() {
        List<LabelValueReadModel> concessionTypeIdentifiers =
                avetmissApplicationService.getConcessionTypeIdentifiers();

        return ResponseEntity.ok(concessionTypeIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/levelOfEducation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getLevelOfEducationIdentifier() {
        List<LabelValueReadModel> levelOfEducationIdentifier =
                avetmissApplicationService.getLevelOfEducationIdentifier();

        return ResponseEntity.ok(levelOfEducationIdentifier);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/disabilityType",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getDisabilityTypeIdentifiers() {
        List<LabelValueReadModel> disabilityTypeIdentifiers =
                avetmissApplicationService.getDisabilityTypeIdentifiers();

        return ResponseEntity.ok(disabilityTypeIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/language",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getLanguageIdentifiers() {
        List<LabelValueReadModel> languageIdentifiers =
                avetmissApplicationService.getLanguageIdentifiers();

        return ResponseEntity.ok(languageIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anzsicCode",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getAnzsicCodes() {
        List<LabelValueReadModel> anzsicCodes =
                avetmissApplicationService.getAnzsicCodes();

        return ResponseEntity.ok(anzsicCodes);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifiersGovernmentFunded",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getFundingSourceStateIdentifiersGovernmentFunded() {
        List<LabelValueReadModel> fundingSourceIdentifiers =
                avetmissApplicationService.getFundingSourceStateIdentifiersGovernmentFunded();

        return ResponseEntity.ok(fundingSourceIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifier/{identifier}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<LabelValueReadModel> getFundingSourceStateIdentifier(@PathVariable(value = "identifier") String fundingSourceStateIdentifier) {
        LabelValueReadModel fundingSourceState =
                avetmissApplicationService.getFundingSourceStateByIdentifier(fundingSourceStateIdentifier);

        if(fundingSourceState == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(fundingSourceState);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifiersNonGovernmentFunded",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getFundingSourceStateIdentifiersNonGovernmentFunded() {
        List<LabelValueReadModel> fundingSourceIdentifiers =
                avetmissApplicationService.getFundingSourceStateIdentifiersNonGovernmentFunded();

        return ResponseEntity.ok(fundingSourceIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceNational",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getFundingSourceNationalIdentifiers() {
        List<LabelValueReadModel> fundingSourceNationalIdentifiers =
                avetmissApplicationService.getFundingSourceNationalIdentifiers();

        return ResponseEntity.ok(fundingSourceNationalIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schoolLevelCompleted",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getSchoolLevelCompletedIdentifiers() {
        List<LabelValueReadModel> schoolLevelCompletedIdentifiers =
                avetmissApplicationService.getSchoolLevelCompletedIdentifiers();

        return ResponseEntity.ok(schoolLevelCompletedIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/priorEducationalAchievement",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getPriorEducationalAchievementIdentifiers() {
        List<LabelValueReadModel> priorEducationalAchievementIdentifiers =
                avetmissApplicationService.getPriorEducationalAchievementIdentifiers();

        return ResponseEntity.ok(priorEducationalAchievementIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/labourForceStatus",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getLabourForceStatusIdentifiers() {
        List<LabelValueReadModel> labourForceStatusIdentifiers =
                avetmissApplicationService.getLabourForceStatusIdentifiers();

        return ResponseEntity.ok(labourForceStatusIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/studyReason",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getStudyReasonIdentifiers() {
        List<LabelValueReadModel> studyReasonIdentifiers =
                avetmissApplicationService.getStudyReasonIdentifiers();

        return ResponseEntity.ok(studyReasonIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/studyReason/{identifier}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<LabelValueReadModel> getStudyReasonByIdentifier(@PathVariable(value = "identifier") String studyingReasonIdentifier) {
        LabelValueReadModel studyReason =
                avetmissApplicationService.getStudyReasonByIdentifier(studyingReasonIdentifier);

        if(studyReason == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(studyReason);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/indigenousStatus",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getIndigenousStatusIdentifiers() {
        List<LabelValueReadModel> indigenousStatusIdentifiers =
                avetmissApplicationService.getIndigenousStatusIdentifiers();

        return ResponseEntity.ok(indigenousStatusIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/englishProficiency",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getEnglishProficiencyIdentifiers() {
        List<LabelValueReadModel> englishProficiencyIdentifiers =
                avetmissApplicationService.getEnglishProficiencyIdentifiers();

        return ResponseEntity.ok(englishProficiencyIdentifiers);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/state",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<LabelValueReadModel>> getStateIdentifiers() {
        List<LabelValueReadModel> stateIdentifiers =
                avetmissApplicationService.getStateIdentifiers();

        return ResponseEntity.ok(stateIdentifiers);
    }

}
