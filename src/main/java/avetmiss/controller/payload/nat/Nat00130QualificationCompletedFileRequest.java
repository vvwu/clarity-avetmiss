package avetmiss.controller.payload.nat;

import java.time.LocalDate;

public class Nat00130QualificationCompletedFileRequest {
    public String rtoIdentifier;
    public String courseIdentifier;
    public String studentID;
    public String courseStartDate;

    public boolean isQualificationIssued;
    public String parchmentNumber;
    public LocalDate parchmentIssueDate;

    public boolean isCourseCompleted;
    public String programStatusIdentifier;
    public String dateCourseEnd;
    public String supervisedTeachingActivityCompletionDate;
    public Integer supervisedHours;
    public String programEnrolmentIdentifier;
}
