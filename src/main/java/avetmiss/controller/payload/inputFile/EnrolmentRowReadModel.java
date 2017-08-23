package avetmiss.controller.payload.inputFile;

public class EnrolmentRowReadModel {

    public int rowNum;
    public int studentId;
    public String studentName;
    public String courseCode;
    public String startDate;
    public String endDate;
    public Integer hoursAttended;
    public Integer totalSupervisedHours;
    public int outcomeIdentifier;
    public String tuitionFee;

    // EnrolmentSubject
    // public String subjectIdentifier; // use unitCode

    public String unitCode;
    public String subjectName;
    public String fieldOfEducationIdentifier;
    public int nominalHour;

}
