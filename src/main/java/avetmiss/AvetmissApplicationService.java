package avetmiss;

import avetmiss.controller.payload.LabelValueReadModel;
import avetmiss.controller.payload.SuburbReadModel;
import avetmiss.controller.payload.UnitReadModel;
import avetmiss.domain.*;
import avetmiss.util.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
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

    public LabelValueReadModel getFundingSourceStateByIdentifier(String identifier) {
        Optional<LabelValue> fundingSourceState = AvetmissConstant.getFundingSourceState(identifier);
        return (fundingSourceState.isPresent()) ? toLabelValueReadModel(fundingSourceState.get()) : null;
    }

    public LabelValueReadModel getStudyReasonByIdentifier(String studyingReasonIdentifier) {
        Optional<LabelValue> studyReason = AvetmissConstant.getStudyReason(studyingReasonIdentifier);
        return (studyReason.isPresent()) ? toLabelValueReadModel(studyReason.get()) : null;
    }

}
