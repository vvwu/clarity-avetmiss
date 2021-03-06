package avetmiss.export;

import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import avetmiss.domain.EnrolmentInput;
import avetmiss.export.exception.EnrolmentCourseNameNotFoundInStudentException;
import avetmiss.util.DateUtil;
import avetmiss.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static avetmiss.util.DateUtil.toDateString;
import static java.lang.String.format;

public class EnrolmentAssembler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public avetmiss.export.Enrolment toEnrolment(
            int studentID, String studentName, EnrolmentInput enrolmentInput, CourseReadModel courseReadModel, List<StudentCourseReadModel> studentCourses) {

        Enrolment enrolment = new Enrolment();

        enrolment.setEnrolmentInput(enrolmentInput);
        enrolment.setCourseId(courseReadModel.courseID);

        StudentCourseReadModel linkedStudentCourse =
                findLinkedStudentCourse(studentID, studentName, courseReadModel.courseID, enrolment.startDate(), studentCourses);

        enrolment.setRequiredStudentCourse(linkedStudentCourse, courseReadModel);

        return enrolment;
    }

    // TODO: test
    // TODO: Raj find the first commenced student course and use the start Date end
    private StudentCourseReadModel findLinkedStudentCourse(int studentId, String studentName, int courseIdToFind, LocalDate enrolmentActivityStartDate, List<StudentCourseReadModel> studentCourses) {
        List<StudentCourseReadModel> matches = findStudentCoursesByCourseId(courseIdToFind, studentCourses);
        if(matches.size() == 0) {
            throw new EnrolmentCourseNameNotFoundInStudentException(studentId, studentName, courseIdToFind);
        }

        if(matches.size() == 1) {
            return matches.get(0);
        }

        Optional<StudentCourseReadModel> match = findFirstStudentCourseWhereDateIsWithinTheCourseDuration(enrolmentActivityStartDate, matches);
        if (match.isPresent()) {
            log.info("student {} has {} course records in '{}',  return the first match where the unit startDate is within",
                    studentId, matches.size(), courseIdToFind);

            return match.get();
        }

        throw new IllegalArgumentException(
                format("Can't find a unique student course in Clarity for the sid: %s has %s course '%s', but enrolment start date: %s is not within any of the clarity course duration",
                        studentId, matches.size(), courseIdToFind, toDateString(enrolmentActivityStartDate)));
    }

    private Optional<StudentCourseReadModel> findFirstStudentCourseWhereDateIsWithinTheCourseDuration(
            LocalDate checkDate, List<StudentCourseReadModel> candidates) {

        Optional<StudentCourseReadModel> first
                = candidates.stream()
                .filter(sc -> DateUtil.isDateInRange(sc.startDate, sc.endDate, Dates.toDate(checkDate)))
                .findFirst();

        return first;
    }

    private List<StudentCourseReadModel> findStudentCoursesByCourseId(
            int courseIdToFind, List<StudentCourseReadModel> studentCourses) {
        // course code is included in the clarity course names
        List<StudentCourseReadModel> matches =
                studentCourses.stream()
                        .filter(sc -> courseIdToFind == sc.courseID)
                        .collect(Collectors.toList());

        return matches;
    }
}
