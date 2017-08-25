package avetmiss.export;

import java.util.Date;

public class CourseIdentifierAndCourseStartKey {
    private String courseIdentifier;
    private Date courseStartDate;
    public CourseIdentifierAndCourseStartKey(String courseIdentifier, Date courseStartDate) {
        this.courseIdentifier = courseIdentifier;
        this.courseStartDate = courseStartDate;
    }

    @Override
    public String toString() {
        return "CourseIdentifierAndCourseStartKey{" +
                "courseIdentifier='" + courseIdentifier + '\'' +
                ", courseStartDate=" + courseStartDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseIdentifierAndCourseStartKey that = (CourseIdentifierAndCourseStartKey) o;

        if (courseIdentifier != null ? !courseIdentifier.equals(that.courseIdentifier) : that.courseIdentifier != null)
            return false;
        return courseStartDate != null ? courseStartDate.equals(that.courseStartDate) : that.courseStartDate == null;
    }

    @Override
    public int hashCode() {
        int result = courseIdentifier != null ? courseIdentifier.hashCode() : 0;
        result = 31 * result + (courseStartDate != null ? courseStartDate.hashCode() : 0);
        return result;
    }
}
