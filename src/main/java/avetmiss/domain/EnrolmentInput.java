package avetmiss.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;

public class EnrolmentInput {

    private int rowNum;
    private int studentId;
    private String courseCode;
    private String unitCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private int nominalHour;
    private Integer hoursAttended;
    private Integer totalSupervisedHours;
    private OutcomeIdentifierNational outcomeIdentifier;
    private String tuitionFee;
    private int clientOtherFee;

    // associations
    private EnrolmentSubject unit;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int nominalHour() {
        return nominalHour;
    }

    public void setNominalHour(int nominalHour) {
        this.nominalHour = nominalHour;
    }

    public Integer totalSupervisedHours() {
        return totalSupervisedHours;
    }

    public void setTotalSupervisedHours(Integer totalSupervisedHours) {
        this.totalSupervisedHours = totalSupervisedHours;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String courseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public int clientOtherFee() {
        return clientOtherFee;
    }

    public void setClientOtherFee(int clientOtherFee) {
        this.clientOtherFee = clientOtherFee;
    }

    public Integer hoursAttended() {
        return hoursAttended;
    }

    public void setHoursAttended(Integer hoursAttended) {
        this.hoursAttended = hoursAttended;
    }


}
