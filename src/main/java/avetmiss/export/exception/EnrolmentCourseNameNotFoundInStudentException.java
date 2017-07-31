package avetmiss.export.exception;

import static java.lang.String.format;

public class EnrolmentCourseNameNotFoundInStudentException extends RuntimeException {

    public EnrolmentCourseNameNotFoundInStudentException(
            int studentID, String studentName, int courseId) {
        super(format("Student ID '%s', name: '%s', cannot find a courseId '%s' in Clarity, the course name provided by the spreadsheet should match the course name in Clarity",
                studentID, studentName, courseId));
    }
}
