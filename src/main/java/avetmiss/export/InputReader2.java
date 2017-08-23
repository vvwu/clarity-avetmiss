package avetmiss.export;

import avetmiss.client.ClarityShareServiceClient;
import avetmiss.client.payload.CourseReadModel;
import avetmiss.client.payload.StudentCourseReadModel;
import avetmiss.client.payload.StudentReadModel;
import avetmiss.controller.payload.inputFile.ClientReadModel;
import avetmiss.domain.EnrolmentInput;
import avetmiss.export.natfile.vat00120.ClientFeesOther;
import avetmiss.util.hudson.TaskListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;

public class InputReader2 {

    public List<Client> readAndValidate(ClarityShareServiceClient clarityShareServiceClient, List<ClientReadModel> clientReadModels, TaskListener out) {
        final List<String> errors = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        for (ClientReadModel clientReadModel : clientReadModels) {

            try {
                Client client = readOneClient(clarityShareServiceClient, clientReadModel, errors);
                clients.add(client);

            } catch (Exception e) {
                errors.add("Student sid:" + clientReadModel.studentId + ": " + e.getMessage());
            }
        }

        if (errors.isEmpty()) {
            out.info("Validation completed with no error");
            return clients;
        }

        out.error("The following errors need to be fixed first: ");
        errors.forEach(out::error);

        return clients;
    }

    Client readOneClient(ClarityShareServiceClient clarityShareServiceClient, ClientReadModel clientReadModel, List<String> errors) {

        final int studentId = clientReadModel.studentId;

        StudentReadModel student = this.existingStudent(clarityShareServiceClient, studentId);
        this.assertStudentDetailIsComplete(student);

        // validate Clarity student course
        List<StudentCourseReadModel> studentCourses = student.courses;

        ClientBuilder clientBuilder = new ClientBuilder(student, selectedVetCoursesToNatStudentCourse(clarityShareServiceClient, studentId, studentCourses));

        if(isEmpty(studentCourses)) {
            errors.add(format("Student %s doesn't enrol in any course", student.toString()));

            return clientBuilder.build();
        }

        EnrolmentAssembler enrolmentAssembler = new EnrolmentAssembler();
        for(EnrolmentInput enrolmentRowReadModel: clientReadModel.enrolments) {
            try {
                String studentName = student.firstName + " " + student.lastName;

                CourseReadModel courseReadModel = requiredCourse(clarityShareServiceClient, enrolmentRowReadModel.courseCode());

                Enrolment enrolment =
                        enrolmentAssembler.toEnrolment(
                                studentId,
                                studentName,
                                enrolmentRowReadModel,
                                courseReadModel,
                                studentCourses);

                ensureCourseIsValid(errors, enrolment.courseEnrolled());

                clientBuilder.withEnrolment(enrolment);
            } catch (Exception e) {
                errors.add("Enrolment " + enrolmentRowReadModel.getRowNum() + ": " + e.getMessage());
            }
        }

        return clientBuilder.build();
    }

    private CourseReadModel requiredCourse(ClarityShareServiceClient clarityShareServiceClient, String courseCode) {
        CourseReadModel course = clarityShareServiceClient.findCourseByCourseCode(courseCode);
        checkArgument(course != null, "course not found, courseCode: '%s'", courseCode);

        return course;
    }

    private List<NatVetStudentCourse> selectedVetCoursesToNatStudentCourse(ClarityShareServiceClient clarityShareServiceClient, int studentID, List<StudentCourseReadModel> studentCourses) {
        List<NatVetStudentCourse> natStudentCourses = new ArrayList<>();

        for (StudentCourseReadModel sc : studentCourses) {
            CourseReadModel course = clarityShareServiceClient.findCourse(sc.courseID);

            boolean isVocational = "Y".equalsIgnoreCase(course.vetFlag);
            if (isVocational) {
                String clientFeesOther = ClientFeesOther.clientFeesOther(sc);

                natStudentCourses.add(new NatVetStudentCourse(studentID, clientFeesOther, sc, course));
            }
        }

        return natStudentCourses;
    }

    private StudentReadModel existingStudent(ClarityShareServiceClient clarityShareServiceClient, int studentId) {
        StudentReadModel student = clarityShareServiceClient.findStudent(studentId);

        checkArgument(student != null,
                format("student with SID: '%s' doesn't exist in the Clarity", studentId));

        return student;
    }

    private void assertStudentDetailIsComplete(StudentReadModel student) {
        int studentId = student.studentID;
        checkArgument(hasLength(student.lastName), format("Student id: %s, last name is required", studentId));
        checkArgument(hasLength(student.postCode), format("Student id: %s, postcode is required", studentId));
        checkArgument(hasLength(student.suburb), format("Student id: %s, suburb is required", studentId));
    }

     private void ensureCourseIsValid(List<String> errors, NatCourse course) {
        if(isBlank(course.courseIdentifier())) {
            errors.add(course + ": course code is not provided");
        }

        if(isBlank(course.courseName())) {
            errors.add(course + ": course name is not provided");
        }
    }
}
