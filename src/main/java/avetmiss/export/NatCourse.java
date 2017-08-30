package avetmiss.export;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The Program (Course) (NAT00030) file provides information about programs (courses) or skill sets that are
 * undertaken and/or completed by clients during the collection period.
 * <p/>
 * Each program (course) record listed in this file must appear in either the Enrolment (NAT00120) file or the Program
 * (Qualification) Completed (NAT00130) file
 */
public class NatCourse {

    public NatCourse(
            String courseIdentifier,
            String courseName,
            Integer nominalHours,
            String programRecognitionIdentifier,
            String levelOfEducationIdentifier,
            String fieldOfEducationIdentifier,
            String occupationTypeIdentifier) {

        checkArgument(nominalHours != null, "nominalHours is null, courseIdentifier=%s", courseIdentifier);

        this.courseIdentifier = courseIdentifier;
        this.courseName = courseName;
        this.nominalHour = nominalHours;
        this.programRecognitionIdentifier = programRecognitionIdentifier;
        this.levelOfEducationIdentifier = levelOfEducationIdentifier;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
        this.occupationTypeIdentifier = occupationTypeIdentifier;
    }

    private String courseIdentifier;
    private String courseName;
    private int nominalHour;
    private String programRecognitionIdentifier;
    private String levelOfEducationIdentifier;
    private String fieldOfEducationIdentifier;
    private String occupationTypeIdentifier;

    public String courseIdentifier() {
        return courseIdentifier;
    }

    public String courseName() {
        return courseName;
    }

    public int nominalHour() {
        return nominalHour;
    }

    public String programRecognitionIdentifier() {
        return programRecognitionIdentifier;
    }

    public String levelOfEducationIdentifier() {
        return levelOfEducationIdentifier;
    }

    public String fieldOfEducationIdentifier() {
        return fieldOfEducationIdentifier;
    }

    public String occupationTypeIdentifier() {
        return occupationTypeIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatCourse)) {
            return false;
        }

        NatCourse that = (NatCourse) o;
        return (courseIdentifier != null && this.courseIdentifier.equals(that.courseIdentifier));
    }

    @Override
    public int hashCode() {
        return (courseIdentifier == null ? 0: courseIdentifier.hashCode());
    }
}
