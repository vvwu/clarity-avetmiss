package avetmiss.controller;

import avetmiss.AvetmissApplicationService;
import avetmiss.controller.payload.LabelValueReadModel;
import avetmiss.domain.AvetmissConstant;
import avetmiss.util.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static avetmiss.data.ReadModelAssembler.toLabelValueReadModels;

@RestController
@RequestMapping("/identifiers")
public class AvetmissIdentifiersController {

    private AvetmissApplicationService avetmissApplicationService;

    @Autowired
    public AvetmissIdentifiersController(AvetmissApplicationService avetmissApplicationService) {
        this.avetmissApplicationService = avetmissApplicationService;
    }

    @GetMapping("/concessionType")
    List<LabelValueReadModel> getConcessionTypeIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getConcessionTypeIdentifiers());
    }

    @GetMapping("/levelOfEducation")
    List<LabelValueReadModel> getLevelOfEducationIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getLevelOfEducationIdentifier());
    }

    @GetMapping("/disabilityType")
    List<LabelValueReadModel> getDisabilityTypeIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getDisabilityTypeIdentifiers());
    }

    @GetMapping("/language")
    List<LabelValueReadModel> getLanguageIdentifiers() {
        List<LabelValue> languages = AvetmissConstant.getLanguageIdentifiers();

        Collections.sort(languages, Comparator.comparing(LabelValue::getLabel));

        return toLabelValueReadModels(languages);
    }

    @GetMapping("/anzsicCode")
    List<LabelValueReadModel> getAnzsicCodes() {
        return toLabelValueReadModels(AvetmissConstant.getAnzsicCodes());
    }

    @GetMapping("/fundingSourceStateIdentifiersGovernmentFunded")
    List<LabelValueReadModel> getFundingSourceStateIdentifiersGovernmentFunded() {
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceStateIdentifiers_GovernmentFunded());
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
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceStateIdentifiers_NonGovernmentFunded());
    }

    @GetMapping("/fundingSourceNational")
    List<LabelValueReadModel> getFundingSourceNationalIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceNationalIdentifiers());
    }

    @GetMapping("/schoolLevelCompleted")
    List<LabelValueReadModel> getSchoolLevelCompletedIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getSchoolLevelCompletedIdentifiers());
    }

    @GetMapping("/priorEducationalAchievement")
    List<LabelValueReadModel> getPriorEducationalAchievementIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getPriorEducationalAchievementIdentifiers());
    }

    @GetMapping("/labourForceStatus")
    List<LabelValueReadModel> getLabourForceStatusIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getLabourForceStatusIdentifiers());
    }

    @GetMapping("/studyReason")
    List<LabelValueReadModel> getStudyReasonIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getStudyReasonIdentifiers());
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
        return toLabelValueReadModels(AvetmissConstant.getIndigenousStatusIdentifiers());
    }

    @GetMapping("/englishProficiency")
    List<LabelValueReadModel> getEnglishProficiencyIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getEnglishProficiencyIdentifiers());
    }

    @GetMapping("/state")
    List<LabelValueReadModel> getStateIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getStateIdentifiers());
    }

}
