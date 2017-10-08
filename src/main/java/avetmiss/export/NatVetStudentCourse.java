package avetmiss.export;

import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.StudentCourseEnrolmentInfoReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static avetmiss.util.DateUtil.year;
import static avetmiss.util.Dates.currentYear;
import static com.google.common.base.Preconditions.checkArgument;

// replacement of StudentCourse in svts domain
public class NatVetStudentCourse {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String clientFeesOther;

    private String uniqueIdentifier;
    private String status;
    private boolean qualificationIssued;
    private boolean isDeferred;
    private String courseIdentifier;
    private String associatedCourseIdentifier;
    private Integer studentID;

    private Date courseEndDate;
    private Date courseStartDate;
    private Date supervisedTeachingActivityCompletionDate;

    private String fundingSourceStateIdentifier;
    private String domesticFeeForServiceType;
    private String trainingContractIdentifierApprenticeships;
    private String concessionTypeIdentifier;
    private String clientIdentifierApprenticeships;
    // private String anzsicCode;

    private NatCourse natCourse;

    public NatVetStudentCourse(int studentID, String clientFeesOther, StudentCourseReadModel studentCourse, CourseReadModel course) {
        checkArgument(studentCourse != null);
        this.clientFeesOther = clientFeesOther;

        if(studentCourse.startDate == null) {
            log.error("student: {}, course StartDate must not be empty", studentID);
        }

        // TODO: reinstate the check & fix test cases
        // clarity.util.Validate2.checkArgument(studentCourse.getCourseEnd() != null, "student: {}, course: {}, courseEnd must not be empty", studentCourse.getStudentID(), studentCourse.courseName());

        this.studentID = studentID;
        this.uniqueIdentifier = studentCourse.studentCourseID + "";
        this.status = studentCourse.status;
        this.qualificationIssued = studentCourse.qualificationIssued;
        this.isDeferred = studentCourse.deferred;

        this.associatedCourseIdentifier = studentCourse.supersededOldCourseIdentifier;

        this.courseStartDate = studentCourse.startDate;
        this.courseEndDate = studentCourse.endDate;

        StudentCourseEnrolmentInfoReadModel enrolmentInfo = studentCourse.enrolmentInfo;

        this.supervisedTeachingActivityCompletionDate = enrolmentInfo.supervisedTeachingActivityCompletionDate;
        this.fundingSourceStateIdentifier = enrolmentInfo.fundingSourceStateIdentifier;
        this.domesticFeeForServiceType = enrolmentInfo.domesticFeeForServiceTypeDisplayValue;
        this.trainingContractIdentifierApprenticeships = enrolmentInfo.trainingContractIdentifierApprenticeships;
        this.concessionTypeIdentifier = enrolmentInfo.concessionTypeIdentifier;
        this.clientIdentifierApprenticeships = enrolmentInfo.clientIdentifierApprenticeships;
        // this.anzsicCode = enrolmentInfo.anzsicCode;

        this.courseIdentifier = course.courseIdentifier;
        this.natCourse = new NatCourse(
                course.courseIdentifier,
                course.name,
                course.nominalHours,
                course.programRecognitionIdentifier,
                course.levelOfEducationIdentifier,
                course.fieldOfEducationIdentifier,
                course.ANZSCOIdentifier);
    }

    public boolean isCourseCompleted() {
        // include isCancelled() to avoid warning 120087: For Client ID xxx in Qualification/Course ID yyy there
        // must be a corresponding record on the Program/Qualifications Completions file.
        return "Finished".equalsIgnoreCase(status);
    }

    public boolean isQualificationIssued() {
        if (qualificationIssued) {
            ensureYearCourseEndDateNotInTheFuture(
                    this.studentID,
                    this.courseIdentifier,
                    yearCourseEnd());
        }

        return qualificationIssued;
    }

    private void ensureYearCourseEndDateNotInTheFuture(Integer studentId, String courseCode, int yearCourseEnd) {
        checkArgument(yearCourseEnd <= currentYear(),
                "Student course is marked as finished, course end must not be in the future. Student: %s, courseCode: %s, year course end: %s",
                studentId, courseCode, yearCourseEnd);
    }

    public String getCourseIdentifier() {
        return courseIdentifier;
    }

    public CourseIdentifierAndCourseStartKey courseIdentifierAndCourseStartKey() {
        return new CourseIdentifierAndCourseStartKey(courseIdentifier, courseStartDate);
    }

    public String getAssociatedCourseIdentifier() {
        return associatedCourseIdentifier;
    }

    public void setAssociatedCourseIdentifier(String associatedCourseIdentifier) {
        this.associatedCourseIdentifier = associatedCourseIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public NatCourse natCourse() {
        return this.natCourse;
    }

    public Date courseStart() {
        return this.courseStartDate;
    }

    public Date courseEnd() {
        return this.courseEndDate;
    }

    public int yearCourseEnd() {
        return year(this.courseEndDate);
    }

    public Date supervisedTeachingActivityCompletionDate() {
        return supervisedTeachingActivityCompletionDate;
    }

    public String clientFeesOther() {
        return clientFeesOther;
    }

    public String fundingSourceStateIdentifier() {
        return fundingSourceStateIdentifier;
    }

    public String domesticFeeForServiceType() {
        return domesticFeeForServiceType;
    }

    public String trainingContractIdentifierApprenticeships() {
        return trainingContractIdentifierApprenticeships;
    }

    public String concessionTypeIdentifier() {
        return concessionTypeIdentifier;
    }

    public String clientIdentifierApprenticeships() {
        return clientIdentifierApprenticeships;
    }

    public String uniqueIdentifier() {
        return this.uniqueIdentifier;
    }

    public boolean isDeferred() {
        return this.isDeferred;
    }

    @Override
    public int hashCode() {
        return uniqueIdentifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof NatVetStudentCourse)) {
            return false;
        }

        NatVetStudentCourse that = (NatVetStudentCourse) obj;
        return new EqualsBuilder().append(uniqueIdentifier, that.uniqueIdentifier).isEquals();
    }

    @Override
    public String toString() {
        return "NatStudentCourse{" +
                "status='" + status + '\'' +
                ", courseIdentifier='" + courseIdentifier + '\'' +
                '}';
    }
}
