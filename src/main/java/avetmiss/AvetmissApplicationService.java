package avetmiss;

import avetmiss.controller.payload.LabelValueReadModel;
import avetmiss.controller.payload.SuburbReadModel;
import avetmiss.controller.payload.UnitReadModel;
import avetmiss.domain.*;
import avetmiss.util.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static avetmiss.data.ReadModelAssembler.*;

@Service
public class AvetmissApplicationService {
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private SuburbRepository suburbRepository;

    public List<SuburbReadModel> findSuburbsByPostCode(String postcode) {
        List<Suburb> suburbs = suburbRepository.getSuburbs(Integer.parseInt(postcode));

        return toSuburbReadModels(suburbs);
    }

    public UnitReadModel findUnitByCode(String unitCode) {
        Unit unit = unitRepository.findByCode(unitCode);
        return (unit == null) ? null : toSuburbReadModel(unit);
    }

    public List<LabelValueReadModel> getConcessionTypeIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getConcessionTypeIdentifiers());
    }

    public List<LabelValueReadModel> getLevelOfEducationIdentifier() {
        return toLabelValueReadModels(AvetmissConstant.getLevelOfEducationIdentifier());
    }

    public List<LabelValueReadModel> getDisabilityTypeIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getDisabilityTypeIdentifiers());
    }

    public List<LabelValueReadModel> getLanguageIdentifiers() {
        List<LabelValue> languages = AvetmissConstant.getLanguageIdentifiers();

        Collections.sort(languages, (a, b) -> a.getLabel().compareTo(b.getLabel()));

        return toLabelValueReadModels(languages);
    }

    public List<LabelValueReadModel> getFundingSourceStateIdentifiersGovernmentFunded() {
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceStateIdentifiers_GovernmentFunded());
    }

    public LabelValueReadModel getFundingSourceStateByIdentifier(String identifier) {
        Optional<LabelValue> fundingSourceState = AvetmissConstant.getFundingSourceState(identifier);
        return (fundingSourceState.isPresent()) ? toLabelValueReadModel(fundingSourceState.get()) : null;
    }

    public List<LabelValueReadModel> getFundingSourceStateIdentifiersNonGovernmentFunded() {
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceStateIdentifiers_NonGovernmentFunded());
    }

    public List<LabelValueReadModel> getFundingSourceNationalIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getFundingSourceNationalIdentifiers());
    }

    public List<LabelValueReadModel> getSchoolLevelCompletedIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getSchoolLevelCompletedIdentifiers());
    }

    public List<LabelValueReadModel> getPriorEducationalAchievementIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getPriorEducationalAchievementIdentifiers());
    }

    public List<LabelValueReadModel> getLabourForceStatusIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getLabourForceStatusIdentifiers());
    }

    public List<LabelValueReadModel> getStudyReasonIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getStudyReasonIdentifiers());
    }

    public LabelValueReadModel getStudyReasonByIdentifier(String studyingReasonIdentifier) {
        Optional<LabelValue> studyReason = AvetmissConstant.getStudyReason(studyingReasonIdentifier);
        return (studyReason.isPresent()) ? toLabelValueReadModel(studyReason.get()) : null;
    }

    public List<LabelValueReadModel> getIndigenousStatusIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getIndigenousStatusIdentifiers());
    }

    public List<LabelValueReadModel> getEnglishProficiencyIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getEnglishProficiencyIdentifiers());
    }

    public List<LabelValueReadModel> getStateIdentifiers() {
        return toLabelValueReadModels(AvetmissConstant.getStateIdentifiers());
    }

    public List<LabelValueReadModel> getAnzsicCodes() {
        return toLabelValueReadModels(AvetmissConstant.getAnzsicCodes());
    }

}
