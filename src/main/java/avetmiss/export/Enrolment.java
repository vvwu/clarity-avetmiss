package avetmiss.export;

import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import avetmiss.domain.AvetmissConstant;
import avetmiss.domain.EnrolmentSubject;
import avetmiss.domain.OutcomeIdentifierNational;
import avetmiss.export.natfile.vat00120.ClientFeesOther;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

public class Enrolment {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private int rowNum;
    private int studentId;
    private String studentName;
    private String courseCode;
    private int courseId;
    private String unitCode;
    private Date startDate;
    private Date endDate;
    private int nominalHour;
    private String hoursAttended;
    private Integer supervisedHours;
    private OutcomeIdentifierNational outcomeIdentifier;
    private String tuitionFee;

    // associations
    private EnrolmentSubject unit;
    private NatVetStudentCourse studentCourse;

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

    public int courseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public EnrolmentSubject getUnit() {
        return unit;
    }

    public void setUnit(EnrolmentSubject unit) {
        checkArgument(unit != null, "unit is required");
        this.unit = unit;
    }

    public NatCourse courseEnrolled() {
        return this.studentCourse.natCourse();
    }

    public Date courseStartedDate() {
        return this.studentCourse.courseStart();
    }

    /**  If a client commenced a qualification or a course:
     - for the first time, the value must be 3
     - in a previous collection year and has not completed the qualification, the value must be 4. **/
    public String commencingCourseIdentifier() {
        Date courseStart = courseStartedDate();

        Calendar cal = new GregorianCalendar();
        int currentYear = cal.get(Calendar.YEAR);

        cal.setTime(courseStart);
        int startYear = cal.get(Calendar.YEAR);
        return (startYear < currentYear) ? "4" : "3";
    }

    public String clientFeesOther() {
        return studentCourse.clientFeesOther();
    }

    public String fundingSourceStateIdentifier() {
        return studentCourse.fundingSourceStateIdentifier();
    }

    public String trainingContractIdentifierApprenticeships() {
        return studentCourse.trainingContractIdentifierApprenticeships();
    }

    public String clientIdentifierApprenticeships() {
        return studentCourse.clientIdentifierApprenticeships();
    }

    public String concessionTypeIdentifier() {
        return studentCourse.concessionTypeIdentifier();
    }

    public String anzsicCode() {
        return studentCourse.anzsicCode();
    }

    public NatVetStudentCourse studentCourse() {
        return this.studentCourse;
    }

    public void setStudentCourse(NatVetStudentCourse natStudentCourse) {
        this.studentCourse = Objects.requireNonNull(natStudentCourse);
    }

    public void setNatStudentCourse(NatVetStudentCourse sc) {
        this.studentCourse = Objects.requireNonNull(sc);
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

    public String hoursAttended() {
        return hoursAttended;
    }

    public void setHoursAttended(String hoursAttended) {
        this.hoursAttended = hoursAttended;
    }

    public Integer supervisedHours() {
        return supervisedHours;
    }

    public void setSupervisedHours(Integer supervisedHours) {
        this.supervisedHours = supervisedHours;
    }

    public void setRequiredStudentCourse(StudentCourseReadModel sc, CourseReadModel course) {
        boolean isHigherEducation = "H".equalsIgnoreCase(course.vetFlag);
        checkArgument(!isHigherEducation, "the enrolled course: '%s' is a Higher Education course, only VET course is allow in Avetmiss report");

        String clientFeesOther = ClientFeesOther.clientFeesOther(sc);

        this.setStudentCourse(new NatVetStudentCourse(this.studentId, clientFeesOther, sc, course));
    }

    public Date enrolmentDateObject() {
        String fundingSourceStateIdentifier = studentCourse.fundingSourceStateIdentifier();
        if (AvetmissConstant.isFeeForServiceFundingSourceStateIdentifier(fundingSourceStateIdentifier)) {
            return null;
        }

        return studentCourse.courseStart();
    }

    protected String simpleEnrolmentIdentifier() {
        String enrolmentIdentifier =
                format("s%s-c%s-u%s",
                        this.studentId,
                        natStudentCourse().uniqueIdentifier(),
                        getUnitCode());

        return enrolmentIdentifier;
    }

    public String enrolmentIdentifierWithStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        String enrolmentIdentifier =
                format("s%s-c%s-u%s-d%s",
                        this.studentId,
                        natStudentCourse().uniqueIdentifier(),
                        getUnitCode(),
                        formatter.format(startDate));

        return enrolmentIdentifier;
    }

    private NatVetStudentCourse natStudentCourse() {
        return this.studentCourse;
    }
}
