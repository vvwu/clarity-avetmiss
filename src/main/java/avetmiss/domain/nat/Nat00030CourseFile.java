package avetmiss.domain.nat;

import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;
import avetmiss.export.NatCourse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static org.apache.commons.lang.StringUtils.leftPad;

/**
 * The Program (Course) (NAT00030) file provides information about programs (courses) or skill sets that are
 * undertaken and/or completed by clients during the collection period.
 * <p/>
 * Each program (course) record listed in this file must appear in either the Enrolment (NAT00120) file or the Program
 * (Qualification) Completed (NAT00130) file
 */
public class Nat00030CourseFile {

    private final static Header header = Header(
            of("Program (Qualification/Course) Identifier", 10),
            of("Program (Qualification/Course) Name", 100),
            of("Nominal Hours", 4),
            of("Program (Qualification/Course) Recognition Identifier", 2),
            of("Program (Qualification/Course) Level of Education Identifier", 3),
            of("Program (Qualification/Course) Field of Education Identifier", 4),
            of("ANZSCO (Occupation Type) Identifier", 6),
            of("VET Flag", 1));

    public String export(Collection<NatCourse> requests) {

        // Each program (course) record listed in this file must appear in either the Enrolment (NAT00120) file or
        // the Program (Qualification) Completed (NAT00130) file

        List<Row> rows = exportCoursesRaw(requests);
        return ExportHelper.writeToString(header, rows);
    }

    List<Row> exportCoursesRaw(Collection<NatCourse> requests) {
        List<Row> rows = new ArrayList();

        for (NatCourse request : requests) {
            rows.add(courseInfo(request));
        }
        return rows;
    }

    private Row courseInfo(NatCourse request) {
        String courseIdentifier = request.courseIdentifier();

        // Yes/Y - the intention of the training program is vocational.
        // No/N - the intention of the training program is not vocational.
        String nominalHours = leftPad(request.nominalHour() + "", 4, '0');

        return new Row(
                courseIdentifier.toUpperCase(),
                request.courseName().toUpperCase(),
                nominalHours,
                request.programRecognitionIdentifier(),
                request.levelOfEducationIdentifier(),
                request.fieldOfEducationIdentifier(),
                request.occupationTypeIdentifier(),
                vetFlag());
    }

    private String vetFlag() {
        // If the Subject (Module/Unit of Competency Flag) in the NAT00060 is set to
        // C - UNIT OF COMPETENCY, the VET Flag must be Y
        return "Y";
    }
}
