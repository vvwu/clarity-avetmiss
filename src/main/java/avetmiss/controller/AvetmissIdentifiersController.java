package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.controller.payload.LabelValueReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/identifiers")
@EnableAutoConfiguration
public class AvetmissIdentifiersController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @RequestMapping(method = RequestMethod.GET, value = "/concessionType", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getConcessionTypeIdentifiers() {
        return avetmissApplicationService.getConcessionTypeIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/levelOfEducation", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getLevelOfEducationIdentifiers() {
        return avetmissApplicationService.getLevelOfEducationIdentifier();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/disabilityType", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getDisabilityTypeIdentifiers() {
        return avetmissApplicationService.getDisabilityTypeIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/language", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getLanguageIdentifiers() {
        return avetmissApplicationService.getLanguageIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anzsicCode", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getAnzsicCodes() {
        return avetmissApplicationService.getAnzsicCodes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifiersGovernmentFunded", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getFundingSourceStateIdentifiersGovernmentFunded() {
        return avetmissApplicationService.getFundingSourceStateIdentifiersGovernmentFunded();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifier/{identifier}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<LabelValueReadModel> getFundingSourceStateIdentifier(@PathVariable(value = "identifier") String fundingSourceStateIdentifier) {
        LabelValueReadModel fundingSourceState =
                avetmissApplicationService.getFundingSourceStateByIdentifier(fundingSourceStateIdentifier);

        if(fundingSourceState == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(fundingSourceState);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceStateIdentifiersNonGovernmentFunded", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getFundingSourceStateIdentifiersNonGovernmentFunded() {
        return avetmissApplicationService.getFundingSourceStateIdentifiersNonGovernmentFunded();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fundingSourceNational", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getFundingSourceNationalIdentifiers() {
        return avetmissApplicationService.getFundingSourceNationalIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schoolLevelCompleted", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getSchoolLevelCompletedIdentifiers() {
        return avetmissApplicationService.getSchoolLevelCompletedIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/priorEducationalAchievement", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getPriorEducationalAchievementIdentifiers() {
        return avetmissApplicationService.getPriorEducationalAchievementIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/labourForceStatus", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getLabourForceStatusIdentifiers() {
        return avetmissApplicationService.getLabourForceStatusIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/studyReason", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getStudyReasonIdentifiers() {
        return avetmissApplicationService.getStudyReasonIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/studyReason/{identifier}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<LabelValueReadModel> getStudyReasonByIdentifier(@PathVariable(value = "identifier") String studyingReasonIdentifier) {
        LabelValueReadModel studyReason =
                avetmissApplicationService.getStudyReasonByIdentifier(studyingReasonIdentifier);

        if(studyReason == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(studyReason);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/indigenousStatus", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getIndigenousStatusIdentifiers() {
        return avetmissApplicationService.getIndigenousStatusIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/englishProficiency", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getEnglishProficiencyIdentifiers() {
        return avetmissApplicationService.getEnglishProficiencyIdentifiers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/state", produces = APPLICATION_JSON_VALUE)
    List<LabelValueReadModel> getStateIdentifiers() {
        return avetmissApplicationService.getStateIdentifiers();
    }

}
