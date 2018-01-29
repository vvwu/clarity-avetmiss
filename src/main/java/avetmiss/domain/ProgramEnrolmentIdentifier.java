package avetmiss.domain;

import avetmiss.util.Dates;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;

public class ProgramEnrolmentIdentifier {

    // To tie attributes of training in a Subject to attributes of the Program that the student is
    // undertaking

    // Program = Course
    // This identifier should remain unique to the combination of Student, Program, Program Commencement Date
    // and ContractID once uploaded. If any of these values change for an identifier where activity has been
    // successfully paid for, the submission will be rejected.
    public static String programEnrolmentIdentifier(boolean international, String studentID, String programIdentifier, LocalDate programStartDate) {
        String contractID = international ? null : "2017208290";
        return StringUtils.join(new String[]{studentID, programIdentifier, Dates.toISO(programStartDate), contractID}, "-");
    }
}
