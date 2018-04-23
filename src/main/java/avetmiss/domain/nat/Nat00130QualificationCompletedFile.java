package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import avetmiss.domain.AvetmissUtil;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.domain.Row;
import avetmiss.util.Csv;
import avetmiss.util.Dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static String QUALIFICATION_ISSUED_CSV =  
            "39002,10362NAT,20/07/2017,CC39002\n" +
            "39155,10362NAT,10/10/2017,CC39155\n" +
            "38621,10362NAT,21/02/2017,CC38621\n" +
            "38684,10362NAT,20/04/2017,CC38684\n" +
            "39352,10363NAT,10/10/2017,CC39352\n" +
            "39090,10363NAT,03/07/2017,CC39090\n" +
            "39264,10363NAT,04/08/2017,CC39264\n" +
            "36282,22255VIC,20/07/2017,CC36282\n" +
            "36595,10363NAT,15/08/2017,CC36595\n" +
            "39107,22255VIC,18/08/2017,CC39107\n" +
            "36282,22255VIC,20/07/2017,CC36282\n" +
            "36210,22255VIC,04/08/2017,CC36210\n" +
            "38829,22255VIC,15/09/2017,CC38829\n" +
            "37553,SIT31113,23/05/2017,CC37553\n" +
            "36282,10363NAT,11/08/2016,CC36282\n" +
            "36513,SIT50416,10/01/2018,CC36513\n" +
            "36539,SIT30816,16/01/2018,CC36539\n" +
            "36603,22255VIC,10/01/2018,CC36603\n" +
            "36648,SIT30816,04/01/2018,CC36648\n" +
            "36765,SIT50416,23/02/2018,CC36765\n" +
            "36849,SIT50313,08/01/2018,CC36849\n" +
            "36882,10363NAT,24/01/2018,CC36882\n" +
            "36941,SIT50416,16/03/2018,CC36941\n" +
            "37114,SIT40516,10/01/2018,CC37114\n" +
            "37212,10363NAT,08/01/2018,CC37212\n" +
            "37310,SIT40516,22/02/2018,CC37310\n" +
            "37347,SIT40516,18/01/2018,CC37347\n" +
            "37393,SIT40516,16/03/2018,CC37393\n" +
            "37492,SIT50416,08/01/2018,CC37492\n" +
            "37504,SIT40516,01/03/2018,CC37504\n" +
            "37646,ICT50415,07/03/2018,CC37646\n" +
            "37657,SIT30816,23/02/2018,CC37657\n" +
            "37681,SIT40516,01/03/2018,CC37681\n" +
            "37764,SIT40716,26/02/2018,CC37764\n" +
            "37764,SIT31016,26/02/2018,CC37764\n" +
            "37769,SIT60316,09/01/2018,CC37769\n" +
            "37794,10363NAT,16/01/2018,CC37794\n" +
            "37794,10362NAT,16/01/2018,CC37794\n" +
            "37816,ICT50415,09/01/2018,CC37816\n" +
            "37838,ICT50415,16/03/2018,CC37838\n" +
            "37844,22255VIC,02/03/2018,CC37844\n" +
            "37844,10363NAT,15/01/2018,CC37844\n" +
            "37869,22255VIC,12/01/2018,CC37869\n" +
            "37910,22255VIC,15/01/2018,CC37910\n" +
            "37924,SIT40716,05/03/2018,CC37924\n" +
            "37935,10363NAT,28/02/2018,CC37935\n" +
            "37949,SIT40516,21/03/2018,CC37949\n" +
            "37949,SIT30816,16/03/2018,CC37949\n" +
            "37982,SIT40716,16/03/2018,CC37982\n" +
            "38023,SIT40716,08/03/2018,CC38023\n" +
            "38054,ICT50415,09/01/2018,CC38054\n" +
            "38061,SIT40516,22/02/2018,CC38061\n" +
            "38079,SIT40516,30/01/2018,CC38079\n" +
            "38100,SIT40516,09/01/2018,CC38100\n" +
            "38211,SIT31016,09/01/2018,CC38211\n" +
            "38386,22255VIC,09/01/2018,CC38386\n" +
            "38406,22255VIC,15/01/2018,CC38406\n" +
            "38443,22255VIC,09/01/2018,CC38443\n" +
            "38577,SIT31016,18/01/2018,CC38577\n" +
            "38621,10363NAT,28/02/2018,CC38621\n" +
            "38683,10363NAT,30/01/2018,CC38683\n" +
            "38684,10363NAT,28/02/2018,CC38684\n" +
            "38721,22255VIC,09/01/2018,CC38721\n" +
            "38745,SIT60316,08/01/2018,CC38745\n" +
            "38756,SIT50416,26/01/2018,CC38756\n" +
            "38826,22255VIC,10/01/2018,CC38826\n" +
            "38827,10363NAT,28/02/2018,CC38827\n" +
            "38871,SIT31016,09/01/2018,CC38871\n" +
            "38882,10363NAT,21/03/2018,CC38882\n" +
            "38901,22255VIC,12/01/2018,CC38901\n" +
            "38952,SIT30816,16/03/2018,CC38952\n" +
            "38961,10363NAT,19/02/2018,CC38961\n" +
            "39035,10363NAT,10/01/2018,CC39035\n" +
            "39069,10363NAT,08/01/2018,CC39069\n" +
            "39090,22255VIC,28/02/2018,CC39090\n" +
            "39098,SIT31016,09/01/2018,CC39098\n" +
            "39107,22258VIC,21/02/2018,CC39107\n" +
            "39122,10363NAT,24/01/2018,CC39122\n" +
            "39177,10363NAT,10/01/2018,CC39177\n" +
            "39257,22255VIC,08/02/2018,CC39257\n" +
            "39355,10363NAT,21/03/2018,CC39355\n" +
            "39370,10363NAT,19/02/2018,CC39370\n" +
            "39418,22255VIC,24/01/2018,CC39418\n" +
            "39512,22255VIC,10/01/2018,CC39512\n" +
            "39633,22255VIC,24/01/2018,CC39633\n" +
            "39649,22255VIC,10/01/2018,CC39649\n" +
            "39743,22255VIC,21/03/2018,CC39743\n" +
            "39796,10363NAT,10/01/2018,CC39796\n" +
            "39872,10363NAT,28/02/2018,CC39872\n" +
            "39874,10363NAT,28/02/2018,CC39874\n" +
            "39879,10363NAT,28/02/2018,CC39879\n" +
            "39886,SIT30816,16/03/2018,CC39886\n" +
            "39887,SIT30816,16/03/2018,CC39887\n" +
            "39933,SIT40516,16/01/2018,CC39933\n" +
            "39936,SIT40516,09/01/2018,CC39936\n" +
            "39944,22255VIC,21/03/2018,CC39944\n" +
            "39956,SIT30816,16/03/2018,CC39956";


    private static class QualificationIssued {
        private String studentID;
        private String courseIdentifier;
        private LocalDate issueDate;
        private String parchmentNumber;

        public QualificationIssued(String studentID, String courseIdentifier, LocalDate issueDate, String parchmentNumber) {
            this.studentID = studentID;
            this.courseIdentifier = courseIdentifier;
            this.issueDate = issueDate;
            this.parchmentNumber = parchmentNumber;
        }

        public String getStudentID() {
            return studentID;
        }

        public String getCourseIdentifier() {
            return courseIdentifier;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public String getParchmentNumber() {
            return parchmentNumber;
        }

        @Override
        public String toString() {
            return "QualificationIssued{" +
                    "studentID='" + studentID + '\'' +
                    ", courseIdentifier='" + courseIdentifier + '\'' +
                    ", issueDate=" + issueDate +
                    ", parchmentNumber='" + parchmentNumber + '\'' +
                    '}';
        }
    }

    private static List<QualificationIssued> qualificationIssued() {
        List<QualificationIssued> qualificationIssues =
                Csv.read(QUALIFICATION_ISSUED_CSV, (line, rowNum) ->
                        new QualificationIssued(line[0], line[1], LocalDate.parse(line[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")), line[3]));

        return qualificationIssues;
    }

    private List<Row> exportRaw(List<Nat00130QualificationCompletedFileRequest> requests) {
        List<Row> rows = new ArrayList();

        List<QualificationIssued> qualificationIssues = qualificationIssued();


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

    private Optional<QualificationIssued> findQualificationIssued(List<QualificationIssued> qualificationIssues, String studentID, String courseIdentifier) {
        return qualificationIssues.stream().filter(qualificationIssued -> qualificationIssued.studentID.equals(studentID) && qualificationIssued.courseIdentifier.equalsIgnoreCase(courseIdentifier)).findFirst();
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
