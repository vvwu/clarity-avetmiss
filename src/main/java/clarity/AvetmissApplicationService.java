package clarity;

import clarity.controller.payload.LabelValueReadModel;
import clarity.controller.payload.SuburbReadModel;
import clarity.controller.payload.UnitReadModel;
import clarity.domain.*;
import clarity.util.LabelValue;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class AvetmissApplicationService {
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private SuburbRepository suburbRepository;
    private List<LabelValueReadModel> priorEducationalAchievementIdentifiers;

    public List<SuburbReadModel> findSuburbsByPostCode(String postcode) {
        List<Suburb> suburbs = suburbRepository.getSuburbs(Integer.parseInt(postcode));

        return toReadModels(suburbs);
    }

    public UnitReadModel findUnitByCode(String unitCode) {
        Unit unit = unitRepository.findByCode(unitCode);

        if(unit == null) {
            return null;
        }

        return toReadModel(unit);
    }

    public List<LabelValueReadModel> getConcessionTypeIdentifiers() {
        List<LabelValue> concessionTypeIdentifiers = AvetmissConstant.getConcessionTypeIdentifiers();

        return toLabelValueReadModels(concessionTypeIdentifiers);
    }

    public List<LabelValueReadModel> getLevelOfEducationIdentifier() {
        List<LabelValue> levelOfEducationIdentifiers = AvetmissConstant.getLevelOfEducationIdentifier();

        return toLabelValueReadModels(levelOfEducationIdentifiers);
    }

    public List<LabelValueReadModel> getDisabilityTypeIdentifiers() {
        List<LabelValue> disabilityTypeIdentifiers = AvetmissConstant.getDisabilityTypeIdentifiers();

        return toLabelValueReadModels(disabilityTypeIdentifiers);
    }

    public List<LabelValueReadModel> getLanguageIdentifiers() {
        List<LabelValue> languageIdentifiers = AvetmissConstant.getLanguageIdentifiers();

        return toLabelValueReadModels(languageIdentifiers);
    }

    public List<LabelValueReadModel> getFundingSourceStateIdentifiersGovernmentFunded() {
        List<LabelValue> fundingSourceStateIdentifiers =
                AvetmissConstant.getFundingSourceStateIdentifiers_GovernmentFunded();

        return toLabelValueReadModels(fundingSourceStateIdentifiers);
    }

    public LabelValueReadModel getFundingSourceStateByIdentifier(String identifier) {
        LabelValue fundingSourceState =
                AvetmissConstant.getFundingSourceState(identifier);

        return toLabelValueReadModel(fundingSourceState);
    }

    public List<LabelValueReadModel> getFundingSourceStateIdentifiersNonGovernmentFunded() {
        List<LabelValue> fundingSourceStateIdentifiers = AvetmissConstant.getFundingSourceStateIdentifiers_NonGovernmentFunded();

        return toLabelValueReadModels(fundingSourceStateIdentifiers);
    }

    public List<LabelValueReadModel> getFundingSourceNationalIdentifiers() {
        List<LabelValue> fundingSourceNationalIdentifiers = AvetmissConstant.getFundingSourceNationalIdentifiers();

        return toLabelValueReadModels(fundingSourceNationalIdentifiers);
    }

    public List<LabelValueReadModel> getSchoolLevelCompletedIdentifiers() {
        List<LabelValue> schoolLevelCompletedIdentifiers = AvetmissConstant.getSchoolLevelCompletedIdentifiers();

        return toLabelValueReadModels(schoolLevelCompletedIdentifiers);
    }

    public List<LabelValueReadModel> getPriorEducationalAchievementIdentifiers() {
        List<LabelValue> priorEducationalAchievementIdentifiers = AvetmissConstant.getPriorEducationalAchievementIdentifiers();

        return toLabelValueReadModels(priorEducationalAchievementIdentifiers);
    }

    public List<LabelValueReadModel> getLabourForceStatusIdentifiers() {
        List<LabelValue> labourForceStatusIdentifiers = AvetmissConstant.getLabourForceStatusIdentifiers();

        return toLabelValueReadModels(labourForceStatusIdentifiers);
    }

    public List<LabelValueReadModel> getStudyReasonIdentifiers() {
        List<LabelValue> studyReasonIdentifiers = AvetmissConstant.getStudyReasonIdentifiers();

        return toLabelValueReadModels(studyReasonIdentifiers);
    }

    public LabelValueReadModel getStudyReasonByIdentifier(String studyingReasonIdentifier) {
        LabelValue studyReason = AvetmissConstant.getStudyReason(studyingReasonIdentifier);

        return toLabelValueReadModel(studyReason);
    }

    public List<LabelValueReadModel> getIndigenousStatusIdentifiers() {
        List<LabelValue> indigenousStatusIdentifiers = AvetmissConstant.getIndigenousStatusIdentifiers();

        return toLabelValueReadModels(indigenousStatusIdentifiers);
    }

    public List<LabelValueReadModel> getEnglishProficiencyIdentifiers() {
        List<LabelValue> englishProficiencyIdentifiers = AvetmissConstant.getEnglishProficiencyIdentifiers();

        return toLabelValueReadModels(englishProficiencyIdentifiers);
    }

    public List<LabelValueReadModel> getStateIdentifiers() {
        List<LabelValue> stateIdentifiers = AvetmissConstant.getStateIdentifiers();

        return toLabelValueReadModels(stateIdentifiers);
    }

    public List<LabelValueReadModel> getAnzsicCodes() {
        List<LabelValue> anzsicCodes = AvetmissConstant.getAnzsicCodes();

        return toLabelValueReadModels(anzsicCodes);
    }

    private List<SuburbReadModel> toReadModels(List<Suburb> suburbs) {
        List<SuburbReadModel> readModels = newArrayList();
        for(Suburb suburb: suburbs) {
            readModels.add(
                    new SuburbReadModel(
                            suburb.getName(),
                            suburb.getPostCode()));
        }
        return readModels;
    }

    private UnitReadModel toReadModel(Unit unit) {
        UnitReadModel readModel = new UnitReadModel();
        readModel.code = unit.getCode();
        readModel.description = unit.getDescription();
        readModel.fieldOfEducationIdentifier = unit.getFieldOfEducationIdentifier();

        return readModel;
    }

    private List<LabelValueReadModel> toLabelValueReadModels(List<LabelValue> labelValues) {
        List<LabelValueReadModel> readModels = Lists.newArrayList();
        for(LabelValue labelValue: labelValues) {
            readModels.add(toLabelValueReadModel(labelValue));
        }
        return readModels;
    }

    private LabelValueReadModel toLabelValueReadModel(LabelValue labelValue) {
        LabelValueReadModel readModel = new LabelValueReadModel();
        readModel.label = labelValue.getLabel();
        readModel.value = labelValue.getValue();
        return readModel;
    }

}
