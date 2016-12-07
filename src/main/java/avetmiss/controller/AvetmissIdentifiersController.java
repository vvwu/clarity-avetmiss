package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.controller.payload.LabelValueReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/identifiers")
public class AvetmissIdentifiersController {

    @Autowired
    private AvetmissApplicationService avetmissApplicationService;

    @GetMapping("/concessionType")
    List<LabelValueReadModel> getConcessionTypeIdentifiers() {
        return avetmissApplicationService.getConcessionTypeIdentifiers();
    }

    @GetMapping("/levelOfEducation")
    List<LabelValueReadModel> getLevelOfEducationIdentifiers() {
        return avetmissApplicationService.getLevelOfEducationIdentifier();
    }

    @GetMapping("/disabilityType")
    List<LabelValueReadModel> getDisabilityTypeIdentifiers() {
        return avetmissApplicationService.getDisabilityTypeIdentifiers();
    }

    @GetMapping("/language")
    List<LabelValueReadModel> getLanguageIdentifiers() {
        return avetmissApplicationService.getLanguageIdentifiers();
    }

    @GetMapping("/anzsicCode")
    List<LabelValueReadModel> getAnzsicCodes() {
        return avetmissApplicationService.getAnzsicCodes();
    }

    @GetMapping("/fundingSourceStateIdentifiersGovernmentFunded")
    List<LabelValueReadModel> getFundingSourceStateIdentifiersGovernmentFunded() {
        return avetmissApplicationService.getFundingSourceStateIdentifiersGovernmentFunded();
    }

    @GetMapping("/fundingSourceStateIdentifier/{identifier}")
    ResponseEntity<LabelValueReadModel> getFundingSourceStateIdentifier(@PathVariable(value = "identifier") String fundingSourceStateIdentifier) {
        LabelValueReadModel fundingSourceState =
                avetmissApplicationService.getFundingSourceStateByIdentifier(fundingSourceStateIdentifier);

        if(fundingSourceState == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(fundingSourceState);
    }


    @GetMapping("/fundingSourceStateIdentifiersNonGovernmentFunded")
    List<LabelValueReadModel> getFundingSourceStateIdentifiersNonGovernmentFunded() {
        return avetmissApplicationService.getFundingSourceStateIdentifiersNonGovernmentFunded();
    }

    @GetMapping("/fundingSourceNational")
    List<LabelValueReadModel> getFundingSourceNationalIdentifiers() {
        return avetmissApplicationService.getFundingSourceNationalIdentifiers();
    }

    @GetMapping("/schoolLevelCompleted")
    List<LabelValueReadModel> getSchoolLevelCompletedIdentifiers() {
        return avetmissApplicationService.getSchoolLevelCompletedIdentifiers();
    }

    @GetMapping("/priorEducationalAchievement")
    List<LabelValueReadModel> getPriorEducationalAchievementIdentifiers() {
        return avetmissApplicationService.getPriorEducationalAchievementIdentifiers();
    }

    @GetMapping("/labourForceStatus")
    List<LabelValueReadModel> getLabourForceStatusIdentifiers() {
        return avetmissApplicationService.getLabourForceStatusIdentifiers();
    }

    @GetMapping("/studyReason")
    List<LabelValueReadModel> getStudyReasonIdentifiers() {
        return avetmissApplicationService.getStudyReasonIdentifiers();
    }

    @GetMapping("/studyReason/{identifier}")
    ResponseEntity<LabelValueReadModel> getStudyReasonByIdentifier(@PathVariable(value = "identifier") String studyingReasonIdentifier) {
        LabelValueReadModel studyReason =
                avetmissApplicationService.getStudyReasonByIdentifier(studyingReasonIdentifier);
        if(studyReason == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(studyReason);
    }

    @GetMapping("/indigenousStatus")
    List<LabelValueReadModel> getIndigenousStatusIdentifiers() {
        return avetmissApplicationService.getIndigenousStatusIdentifiers();
    }

    @GetMapping("/englishProficiency")
    List<LabelValueReadModel> getEnglishProficiencyIdentifiers() {
        return avetmissApplicationService.getEnglishProficiencyIdentifiers();
    }

    @GetMapping("/state")
    List<LabelValueReadModel> getStateIdentifiers() {
        return avetmissApplicationService.getStateIdentifiers();
    }

}
