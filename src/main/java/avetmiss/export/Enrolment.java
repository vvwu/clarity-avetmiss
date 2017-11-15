package avetmiss.export;

import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import avetmiss.domain.AvetmissConstant;
import avetmiss.domain.EnrolmentInput;
import avetmiss.domain.EnrolmentSubject;
import avetmiss.domain.OutcomeIdentifierNational;
import avetmiss.util.Dates;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

public class Enrolment {
    private int courseId;
    private EnrolmentInput enrolmentInput;
    private NatVetStudentCourse studentCourse;

    public void setEnrolmentInput(EnrolmentInput enrolmentInput) {
        this.enrolmentInput = enrolmentInput;
    }

    public String getUnitCode() {
        return enrolmentInput.getUnitCode();
    }

    public LocalDate startDate() {
        return enrolmentInput.startDate();
    }

    public LocalDate endDate() {
        return enrolmentInput.endDate();
    }

    public int nominalHour() {
        return enrolmentInput.nominalHour();
    }
    public int getRowNum() {
        return enrolmentInput.getRowNum();
    }

    public String courseCode() {
        return enrolmentInput.courseCode();
    }

    public int courseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public EnrolmentSubject getUnit() {
        return enrolmentInput.getUnit();
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

    public int clientFeesOther() {
        return enrolmentInput.clientOtherFee();
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

    public String associatedCourseIdentifier() {
        return studentCourse.getAssociatedCourseIdentifier();
    }

    public String concessionTypeIdentifier() {
        return studentCourse.concessionTypeIdentifier();
    }

//    public String anzsicCode() {
//        return studentCourse.anzsicCode();
//    }

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
        return enrolmentInput.getStudentId();
    }

    public OutcomeIdentifierNational getOutcomeIdentifier() {
        return enrolmentInput.getOutcomeIdentifier();
    }

    public String tuitionFee() {
        return enrolmentInput.tuitionFee();
    }

    public String hoursAttended() {
        Integer hoursAttended = enrolmentInput.hoursAttended();
        return (hoursAttended == null) ? null : hoursAttended.toString();
    }

    public Integer getTotalSupervisedHours() {
        return enrolmentInput.totalSupervisedHours();
    }


    public void setRequiredStudentCourse(StudentCourseReadModel sc, CourseReadModel course) {
        boolean isHigherEducation = "H".equalsIgnoreCase(course.vetFlag);
        checkArgument(!isHigherEducation, "the enrolled course: '%s' is a Higher Education course, only VET course is allow in Avetmiss report");

        this.setStudentCourse(new NatVetStudentCourse(this.getStudentId(), sc, course));
    }

    public LocalDate enrolmentDateObject() {
        String fundingSourceStateIdentifier = studentCourse.fundingSourceStateIdentifier();
        if (AvetmissConstant.isFeeForServiceFundingSourceStateIdentifier(fundingSourceStateIdentifier)) {
            return null;
        }

        return Dates.toLocalDate(studentCourse.courseStart());
    }

    protected String simpleEnrolmentIdentifier() {
        String enrolmentIdentifier =
                format("s%s-c%s-u%s",
                        this.getStudentId(),
                        natStudentCourse().uniqueIdentifier(),
                        getUnitCode());

        return enrolmentIdentifier;
    }

    public String enrolmentIdentifierWithStartDate() {
        String enrolmentIdentifier =
                format("s%s-c%s-u%s-d%s",
                        this.getStudentId(),
                        natStudentCourse().uniqueIdentifier(),
                        getUnitCode(),
                        startDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        return enrolmentIdentifier;
    }

    private NatVetStudentCourse natStudentCourse() {
        return this.studentCourse;
    }
}
