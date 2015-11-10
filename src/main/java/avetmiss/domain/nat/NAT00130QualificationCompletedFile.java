package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import avetmiss.domain.AvetmissUtil;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static com.google.common.collect.Lists.newArrayList;

/**
 * This file contains a record for each Program (Course) enrolment on the Enrolment (NAT00120) file reported during a
 * collection period, where upon completion it is expected that the client will be entitled to a Qualification or a
 * Statement of Attainment
 *
 * There may be multiple Client Identifier/ Course Identifier records on the Program (Qualification) Completions
 * files but only unique records for the Client Identifier/ Course Identifier/ Program (Course) Commencement Date
 * combination.
 */
public class NAT00130QualificationCompletedFile {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final static Header header = Header(43,
            of("Training Organisation Identifier", 10),
            of("Program (Qualification/Course) Identifier", 10),
            of("Client (Student) Identifier", 10),
            of("Year Program Completed", 4),
            of("Qualification Issued Flag", 1),
            of("Program (Course) Commencement Date", 8));

    public String export(List<Nat00130QualificationCompletedFileRequest> requests) {
        List<String[]> rows = exportRaw(requests);
        return ExportHelper.writeToString(header.sizes(), rows);
    }


    List<String[]> exportRaw(List<Nat00130QualificationCompletedFileRequest> requests) {
        List<String[]> rows = newArrayList();

        for (Nat00130QualificationCompletedFileRequest request : requests) {
            String qualificationIssuedFlag = request.isQualificationIssued ? "Y" : "N";
            Date courseStartDate = Dates.toDateISO(request.courseStartDate);
            String yearCourseCompleted = yearCourseCompleted(request);

            String[] row = new String[]{
                    AvetmissUtil.formattedRtoIdentifier(request.rtoIdentifier),
                    request.courseIdentifier.toUpperCase(),
                    request.studentID,
                    yearCourseCompleted,
                    qualificationIssuedFlag,
                    AvetmissUtil.toDate(courseStartDate)};

            rows.add(row);
        }
        return rows;
    }

    private String yearCourseCompleted(Nat00130QualificationCompletedFileRequest request) {
        return request.isCourseCompleted ? request.yearCourseEnd + "" : "@@@@";
    }

}