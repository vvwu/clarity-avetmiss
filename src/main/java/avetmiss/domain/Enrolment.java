package avetmiss.domain;

import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

public class Enrolment {

    private int rowNum;
    private int studentId;
    private String studentName;
    private String courseIdentifier;
    private String courseName;
    private String unitCode;
    private Date startDate;
    private Date endDate;
    private int nominalHour;
    private Integer supervisedHours;
    private Integer hoursAttended;
    private OutcomeIdentifierNational outcomeIdentifier;
    private String tuitionFee;

    // associations
    private EnrolmentSubject unit;
    // private NatStudentCourse studentCourse;

    public String studentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Date startDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date endDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int nominalHour() {
        return nominalHour;
    }

    public void setNominalHour(int nominalHour) {
        this.nominalHour = nominalHour;
    }

    public Integer supervisedHours() {
        return supervisedHours;
    }

    public void setSupervisedHours(Integer supervisedHours) {
        this.supervisedHours = supervisedHours;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String courseIdentifier() {
        return courseIdentifier;
    }

    public void setCourseIdentifier(String courseIdentifier) {
        this.courseIdentifier = courseIdentifier;
    }

    public String courseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public EnrolmentSubject getUnit() {
        return unit;
    }

    public void setUnit(EnrolmentSubject unit) {
        checkArgument(unit != null, "unit is required");
        this.unit = unit;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public OutcomeIdentifierNational getOutcomeIdentifier() {
        return outcomeIdentifier;
    }

    public void setOutcomeIdentifier(OutcomeIdentifierNational outcomeIdentifier) {
        checkArgument(outcomeIdentifier != null, "OutcomeIdentifierNational is required");
        this.outcomeIdentifier = outcomeIdentifier;
    }

    public String tuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(String tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public Integer hoursAttended() {
        return hoursAttended;
    }

    public void setHoursAttended(Integer hoursAttended) {
        this.hoursAttended = hoursAttended;
    }
}
