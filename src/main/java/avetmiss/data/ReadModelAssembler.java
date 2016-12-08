package avetmiss.data;

import avetmiss.controller.payload.LabelValueReadModel;
import avetmiss.controller.payload.SuburbReadModel;
import avetmiss.controller.payload.UnitReadModel;
import avetmiss.domain.Suburb;
import avetmiss.domain.Unit;
import avetmiss.util.LabelValue;

import java.util.List;
import java.util.stream.Collectors;

public class ReadModelAssembler {

    public static List<LabelValueReadModel> toLabelValueReadModels(List<LabelValue> labelValues) {
        return labelValues.stream()
                .map(s -> toLabelValueReadModel(s))
                .collect(Collectors.toList());
    }

    public static LabelValueReadModel toLabelValueReadModel(LabelValue s) {
        return new LabelValueReadModel(s.getLabel(), s.getValue());
    }

    public static List<SuburbReadModel> toSuburbReadModels(List<Suburb> suburbs) {
        return suburbs.stream()
                .map(s -> new SuburbReadModel(s.getName(), s.getPostCode()))
                .collect(Collectors.toList());
    }

    public static UnitReadModel toSuburbReadModel(Unit unit) {
        UnitReadModel readModel = new UnitReadModel();
        readModel.code = unit.code();
        readModel.description = unit.name();
        readModel.fieldOfEducationIdentifier = unit.fieldOfEducationIdentifier();

        return readModel;
    }
}
