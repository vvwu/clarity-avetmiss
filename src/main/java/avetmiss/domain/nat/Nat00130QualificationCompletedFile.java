package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import avetmiss.domain.AvetmissUtil;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;
import avetmiss.util.Dates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.domain.Header.Header;
import static org.apache.commons.lang.StringUtils.leftPad;
import static org.apache.commons.lang.StringUtils.repeat;

/**
 * This file contains a record for each Program (Course) enrolment on the Enrolment (NAT00120) file reported during a
 * collection period, where upon completion it is expected that the client will be entitled to a Qualification or a
 * Statement of Attainment
 *
 * There may be multiple Client Identifier/ Course Identifier records on the Program (Qualification) Completions
 * files but only unique records for the Client Identifier/ Course Identifier/ Program (Course) Commencement Date
 * combination.
 */
public class Nat00130QualificationCompletedFile {

    private final static Header header = Header(145,
            of("Training Organisation Identifier", 10),
            of("Program (Qualification/Course) Identifier", 10),
            of("Client (Student) Identifier", 10),
            of("Date Program Completed", 8),
            of("Qualification Issued Flag", 1),
            of("Parchment Issue Date", 8),
            of("Parchment Number", 25),
            of("Program (Course) Commencement Date", 8),
            of("Program Supervised Teaching Activity Completion Date", 8),
            of("Program Unique Supervised Hours", 5),
            of("Program Status Identifier", 2),
            of("Program Enrolment Identifier", 50));

    public String export(List<Nat00130QualificationCompletedFileRequest> requests) {
        List<Row> rows = exportRaw(requests);
        return ExportHelper.writeToString(header, rows);
    }

    private List<Row> exportRaw(List<Nat00130QualificationCompletedFileRequest> requests) {
        List<Row> rows = new ArrayList();

        for (Nat00130QualificationCompletedFileRequest request : requests) {
            LocalDate courseStartDate = Dates.toLocalDateISO(request.courseStartDate);

            LocalDate supervisedTeachingActivityCompletionDate =
                    Dates.toLocalDateISO(request.supervisedTeachingActivityCompletionDate);


            // Parchment issue date contains the date a parchment (certificate for a qualification or course) was issued
            // for the completion of a program by a student

            // When a parchment is issued to a student for the completion of a program this should be the date entered on the
            // parchment (the creation date) not the application date or the graduation date.

            // Parchment issue date should not be before Date program completed. Parchment issue date cannot be blank if
            // Parchment number is not blank.

            // Specific:
            // Parchment Issue Date should not be before Date program Completed.(130031)
            // Parchment Issue Date cannot be blank if Parchment number is not blank.(vr.130027)
            // When Parchment Issue Date is blank, Issue Flag should be N
            String parchmentIssueDate = AvetmissUtil.toDate(request.parchmentIssueDate);

            // Parchment number contains the recorded number on a certificate for a qualification or course which is issued
            // for the completion of a program by a student

            // Parchment number cannot be blank if Parchment issue date is not blank.
            // This field may be blank
            String parchmentNumber = request.parchmentNumber;
            String qualificationIssuedFlag = (request.isQualificationIssued) ? "Y" : "N";

            String supervisedHours = (request.supervisedHours == null) ? repeat("0", 5) : leftPad(request.supervisedHours.toString(), 5, "0");
            String dateCompleted = dateCourseCompleted(request);

            Row row = new Row(
                    AvetmissUtil.formattedRtoIdentifier(request.rtoIdentifier),
                    request.courseIdentifier.toUpperCase(),
                    request.studentID,
                    dateCompleted,
                    qualificationIssuedFlag,
                    parchmentIssueDate,
                    parchmentNumber,
                    AvetmissUtil.toDate(courseStartDate),
                    AvetmissUtil.toDate(supervisedTeachingActivityCompletionDate),
                    supervisedHours,
                    request.programStatusIdentifier,
                    request.programEnrolmentIdentifier);

            rows.add(row);
        }
        return rows;
    }

//    public static void main(String[] args) {
//        List<QualificationIssued> qualificationIssues = Nat00130QualificationCompletedFile.qualificationIssued();
//        Nat00130QualificationCompletedFile completedFile = new Nat00130QualificationCompletedFile();
//
//        Optional<QualificationIssued> qualificationIssued = completedFile.findQualificationIssued(qualificationIssues, "38882", "10363NAT");
//        System.out.println(qualificationIssued.get());
//    }


    // Date program completed must be the date that the activity in the program was completed, including any on-the-job
    // training components and the time required for the trainer to determine the final outcome.

    // Date program completed should not be defaulted to the date in which the training organisation issued the
    // certificate of completion.

    // Date program completed should not be after Parchment issue date. This field may be blank.

    // DDMMYYYY   A valid year date, not in the future or more than 10 years before the collection period.
    // Blank      Not yet completed
    private String dateCourseCompleted(Nat00130QualificationCompletedFileRequest request) {
        boolean notCompletedYet = "30".equals(request.programStatusIdentifier);

        return notCompletedYet ? "" : AvetmissUtil.toDate(Dates.toLocalDateISO(request.dateCourseEnd));
    }

}
