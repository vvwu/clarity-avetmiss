package avetmiss.domain.nat;

import avetmiss.domain.*;

import java.util.List;
import java.util.stream.Collectors;

import static avetmiss.domain.Field.of;
import static org.apache.commons.lang.StringUtils.leftPad;

public class Nat00060SubjectFile {

    private final static Header header = Header.Header(123,
            of("Subject Identifier (Module/Unit of Competency Identifier)", 12),
            of("Subject (Module/Unit of Competency) Name", 100),
            of("Subject (Module/Unit of Competency) Field of Education Identifier", 6),
            of("VET Flag", 1),
            of("Nominal Hours", 4));

    public String export(List<EnrolmentSubject> requests) {
        List<Row> dataRows = requests
                        .stream()
                        .map(s -> exportRow(s))
                        .collect(Collectors.toList());

        return ExportHelper.writeToString(header, dataRows);
    }

    public Row exportRow(EnrolmentSubject request) {
        // All alphabetic characters in the Module/Unit of Competency Identifier field must be in upper case
        // The name must be in upper case.

        return new Row(
                request.subjectIdentifier().toUpperCase(),
                request.subjectName().toUpperCase(),
                request.fieldOfEducationIdentifier(),
                VetFlag.VOCATIONAL.flag,
                nominalHours(request.norminalHours()));
    }

    private String nominalHours(int nominalHours) {
        return leftPad(nominalHours + "", 4, '0');
    }
}
