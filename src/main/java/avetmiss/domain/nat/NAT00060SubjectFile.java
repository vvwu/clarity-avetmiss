package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00060SubjectFileRequest;
import avetmiss.domain.*;

import java.util.List;

import static avetmiss.domain.Field.of;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.leftPad;

public class Nat00060SubjectFile {

    private UnitRepository unitRepository;

    public Nat00060SubjectFile(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public enum SubjectFlag {
        SUBJECT_IDENTIFIER("M"),
        UNIT_OF_COMPETENCY_IDENTIFIER("C");

        private String flag;
        private SubjectFlag(String flag) {this.flag = flag;}

        public String flag() {
            return flag;
        }
    }

    private final static Header header = Header.Header(
            of("Subject (Module/Unit of Competency) Flag", 1),
            of("Subject Identifier (Module/Unit of Competency Identifier)", 12),
            of("Subject (Module/Unit of Competency) Name", 100),
            of("Subject (Module/Unit of Competency) Field of Education Identifier", 6),
            of("VET Flag", 1),
            of("Nominal Hours", 4));

    public String export(List<Nat00060SubjectFileRequest> requests) {

        List<String[]> dataRows = newArrayList();

        for(Nat00060SubjectFileRequest request: requests) {
            String[] dataRow = exportRaw(request);
            dataRows.add(dataRow);
        }

        return ExportHelper.writeToString(header.sizes(), dataRows);
    }

    public String[] exportRaw(Nat00060SubjectFileRequest request) {
        // All alphabetic characters in the Module/Unit of Competency Identifier field must be in upper case
        // The name must be in upper case.

        Unit unit = unitRepository.findByCode(request.subjectIdentifier);
        checkArgument(unit != null, "unit not found, identifier: {}", request.subjectIdentifier);

        return new String[]{
                SubjectFlag.UNIT_OF_COMPETENCY_IDENTIFIER.flag(),
                unit.getCode().toUpperCase(),
                unit.getDescription().toUpperCase(),
                unit.getFieldOfEducationIdentifier(),
                VetFlag.VOCATIONAL.flag,
                nominalHours(request.nominalHours)};
    }

    private String nominalHours(int nominalHours) {
        return leftPad(nominalHours + "", 4, '0');
    }
}
