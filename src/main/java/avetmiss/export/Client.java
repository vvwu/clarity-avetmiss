package avetmiss.export;

import avetmiss.client.CoeStatus;
import avetmiss.client.payload.EnrolmentInfoReadModel;
import avetmiss.client.payload.StudentReadModel;
import avetmiss.domain.AvetmissUtil;
import avetmiss.util.hudson.TaskListener;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static avetmiss.domain.AvetmissConstant.DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED;
import static avetmiss.domain.AvetmissConstant.DISABILITY_TYPE_OTHER;
import static avetmiss.util.StringUtil.isEqualToAny;
import static avetmiss.util.StringUtil.nullSafeTrimString;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.Validate.isTrue;

public class Client {
	private final List<Enrolment> enrolments;
    private final List<NatVetStudentCourse> studentCourses;

    private EnrolmentInfoReadModel enrolmentInfo;

    private String studentID;
    private boolean international;

    private String vsn;
    private String usi;

    private String sex;
    private Date dob;
    private String title;
    private String firstName;
    private String lastName;
    private String suburb;
    private String postCode;

    private String email;
    private String phone;
    private String mobile;

    public Client(
            StudentReadModel student,
            List<Enrolment> enrolments,
            List<NatVetStudentCourse> studentCourses) {
        Objects.requireNonNull(student, "student is required");
        Objects.requireNonNull(enrolments, "enrolments is required");
        Objects.requireNonNull(studentCourses, "studentCourses is required");

        this.studentID = student.studentID + "";
        this.international = student.international;

        this.vsn = student.vsn;
        this.usi = student.usi;

        this.enrolmentInfo = student.enrolmentInfo;

        this.sex = student.sex;
        this.dob = student.dob;
        this.title = student.title;
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.suburb = student.suburb;
        this.postCode = student.postCode;

        this.email = student.email;
        this.phone = student.phone;
        this.mobile = student.mobile;

        this.enrolments = new ArrayList<>(enrolments);
        this.studentCourses = new ArrayList<>(studentCourses);
    }

	public List<Enrolment> enrolments() {
		return this.enrolments;
	}

    public List<NatVetStudentCourse> qualificationCompletedCourses() {
        // 120087: For at least one course enrolment for a Client ID, Course ID and Course Commencement Date combination on the Enrolment file NAT00120,
        // a record must also exist on the Program/Qualification Completions NAT00130

        // 130011: For Course Commencements in the current year, the Course Commencement Date in NAT00130 must match to a Course
        // Commencement Date in NAT00120 for the same enrolment.

        if (international) {
            Set<NatVetStudentCourse> courses = new LinkedHashSet<>();
            for(Enrolment enrolment: this.enrolments) {
                courses.add(enrolment.studentCourse());
            }
            return new ArrayList<>(courses);
        } else {

            // TODO: consolidate domestic/international logic
            return qualificationCompletedCoursesForDomesticStudentWithCurrentLogic();
        }
    }

    private List<NatVetStudentCourse> qualificationCompletedCoursesForDomesticStudentWithCurrentLogic() {
        // courseIdentifier => course
        // ensure each course only appear once
        // TODO: student/course/courseCommencementDate should be unique (not student/course)
        Map<String, NatVetStudentCourse> uniqueCourses = new LinkedHashMap<>();

        for(NatVetStudentCourse finishedCourse: studentCoursesWithCoeStatus(CoeStatus.FINISHED)) {
            String courseIdentifier = finishedCourse.getCourseIdentifier();
            if(!uniqueCourses.containsKey(courseIdentifier)) {
                uniqueCourses.put(courseIdentifier, finishedCourse);
            }
        }

        for(NatVetStudentCourse studyingCourse: studentCoursesWithCoeStatus(CoeStatus.STUDYING)) {
            String courseIdentifier = studyingCourse.getCourseIdentifier();
            if(!uniqueCourses.containsKey(courseIdentifier)) {
                uniqueCourses.put(courseIdentifier, studyingCourse);
            }
        }

        for(NatVetStudentCourse cancelledCourse: studentCoursesWithCoeStatus(CoeStatus.CANCELLED)) {
            String courseIdentifier = cancelledCourse.getCourseIdentifier();
            if(!cancelledCourse.isDeferred() &&
                    !uniqueCourses.containsKey(courseIdentifier)) {
                uniqueCourses.put(courseIdentifier, cancelledCourse);
            }
        }

        return new ArrayList<>(uniqueCourses.values());
    }

    private List<NatVetStudentCourse> studentCoursesWithCoeStatus(CoeStatus coeStatus) {
        return this.internalStudentCourses()
                .stream()
                .filter(course -> coeStatus.getName().equalsIgnoreCase(course.getStatus()))
                .collect(Collectors.toList());
    }


    public boolean hasDisability() {
        return "Y".equals(enrolmentInfo.disabilityFlag);
    }

	public String studentId() {
		return studentID;
	}

    public String dateOfBirth() {
        return dob == null ? "@@@@@@@@" : AvetmissUtil.toDate(dob);
    }

    public Date dateOfBirthObject() {
        return dob;
    }

    public String sex() {
        String sex = this.sex;
        isTrue(sex != null, "sex is missing, SID=" + studentId());
        isTrue(isEqualToAny(sex.toUpperCase(), new String[]{"F", "M", "@"}));
        return sex.toUpperCase();
    }

    /**
     * AVETMISS field: Name for Encryption must be recorded in the following
     * order: family name (comma) (space) first given name (space) second given
     * name.
     */
    public String nameForEncryption() {
        return format("%s, %s",
                nullSafeTrimString(this.lastName),
                nullSafeTrimString(this.firstName));
    }

    public Integer totalSupervisedHours(String courseCode) {
        Integer aggregatedSupervisedHours = null;

        for (Enrolment enrolment : this.enrolments) {
            if (StringUtils.equals(courseCode, enrolment.courseCode())) {
                Integer aSupervisedHour = enrolment.supervisedHours();

                if (aSupervisedHour != null) {
                    aggregatedSupervisedHours =
                            (aggregatedSupervisedHours == null) ? aSupervisedHour : (aggregatedSupervisedHours + aSupervisedHour);
                }
            }
        }

        return aggregatedSupervisedHours;
    }

    public String countryIdentifier() {
        String countryIdentifier = this.enrolmentInfo.countryIdentifier;
        return isBlank(countryIdentifier) ? "@@@@" : countryIdentifier;
    }

    public String proficiencyInSpokenEnglishIdentifier() {
        String mainLanguageSpokenAtHomeIdentifier = this.enrolmentInfo.mainLanguageSpokenAtHomeIdentifier;

        // Leave this field blank if the Language (Main Language Other Than English Spoken at Home) Identifier field is one of the following:
        // 1201 - ENGLISH
        // 9700 - SIGN LANGUAGE
        // 9701 - AUSLAN
        // 9702 - MAKATON
        // 9799 - SIGN LANGUAGE NOT ELSEWHERE CLASSIFIED
        // or @@@@
        if(isEqualToAny(mainLanguageSpokenAtHomeIdentifier, new String[]{"1201", "9700", "9701", "9702", "9799", "@@@@"}))
            return null;

        return enrolmentInfo.proficiencyInSpokenEnglishIdentifier;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Client)) {
			return false;
		}

		Client that = (Client) obj;
        return StringUtils.equals(this.studentID, that.studentID);
    }
	
	@Override
	public int hashCode() {
		return studentID.hashCode();
	}

    public String contactPhoneNo() {
        return this.phone;
    }

    public String contactMobile() {
        return this.mobile;
    }

    public String contactEmailAddress() {
        return this.email;
    }

    public String postCode(TaskListener out) {
        String postCode = this.postCode;
        if(isBlank(postCode)) {
            return "@@@@";
        } else if(Integer.valueOf(postCode) > 9999 || Integer.valueOf(postCode) < 1 || postCode.length() != 4) {
            out.error("[Export Client]: student '%s' data error: '%s' is not a valid value for 'PostCode', " +
                    "valid range is [0001, 9999]",
                    studentID, postCode);
            return "0000"; // post code unknown
        }
        return postCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public Address address() {
        return Address.EMPTY;
    }

    public String[] disabilityTypeIdentifiers() {
        // Note: If a client has specified multiple disability types within the range 11- 18 then
        // the following Disability Type Identifier values must not be used:
        // 19-OTHER,OR ? 99 - NOT SPECIFIED.
        String[] identifiers = getDisabilityTypeIdentifier(enrolmentInfo.disabilityTypeIdentifiers);
        if(identifiers.length > 1) {
            return removeNotSpecifiedAndOther(identifiers);
        }
        return identifiers;
    }

    private String[] getDisabilityTypeIdentifier(String aDisabilityTypeIdentifiers) {
        return isBlank(aDisabilityTypeIdentifiers) ? new String[0] : split(aDisabilityTypeIdentifiers, ',');
    }

    public Set<NatCourse> currentEnrolmentCourses() {
        return enrolments().stream().map(Enrolment::courseEnrolled).collect(Collectors.toSet());
    }

    private String[] removeNotSpecifiedAndOther(String[] identifiers) {
        String[] tmp = (String[]) ArrayUtils.removeElement(identifiers, DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED);
        return (String[]) ArrayUtils.removeElement(tmp, DISABILITY_TYPE_OTHER);
    }

    private List<NatVetStudentCourse> internalStudentCourses() {
        return this.studentCourses;
    }

    public String suburb() {
        return suburb;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String title() {
        return this.title;
    }

    public EnrolmentInfoReadModel enrolmentInfo() {
        return enrolmentInfo;
    }

    public String vsn() {
        return this.vsn;
    }

    public String usi() {
        return this.usi;
    }

    /**
     * Value that uniquely identifies a student’s enrolment in a module/unit of competency.
     * <p/>
     * To provide a mechanism for linking information relating to individual enrolments at module level.
     * <p/>
     * The Enrolment file must not contain any duplicate records and the
     * Enrolment Identifier must be unique for each record on the Enrolment file. This value must remain unique over
     * time.
     * <p/>
     * The same Enrolment Identifier must be reported against the same records in all subsequent submissions.
     */
    public String enrolmentIdentifier(int enrolmentRowNum) {

        Enrolment matchEnrolment = null;
        Bag identifiers = new HashBag();

        for (Enrolment enrolment : this.enrolments()) {
            identifiers.add(enrolment.simpleEnrolmentIdentifier());

            if(enrolment.getRowNum() == enrolmentRowNum) {
                matchEnrolment = enrolment;
            }
        }


        checkArgument(matchEnrolment != null, "enrolment not found, clientID: %s, rowNum: %s", this.studentID, enrolmentRowNum);

        String identifier = matchEnrolment.simpleEnrolmentIdentifier();
        if(identifiers.getCount(identifier) == 1) {
            return identifier;
        }

        return matchEnrolment.enrolmentIdentifierWithStartDate();
    }

}
