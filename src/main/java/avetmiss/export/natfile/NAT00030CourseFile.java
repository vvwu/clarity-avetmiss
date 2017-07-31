package avetmiss.export.natfile;


import avetmiss.controller.payload.nat.Nat00030CourseFileRequest;
import avetmiss.export.Client;
import avetmiss.export.NatCourse;
import avetmiss.export.ValidationException;
import avetmiss.util.hudson.TaskListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * The Program (Course) (NAT00030) file provides information about programs (courses) or skill sets that are
 * undertaken and/or completed by clients during the collection period.
 * <p/>
 * Each program (course) record listed in this file must appear in either the Enrolment (NAT00120) file or the Program
 * (Qualification) Completed (NAT00130) file
 */
public class NAT00030CourseFile {

    private NAT00030CourseFileReader reader = new NAT00030CourseFileReader();

    public List<Nat00030CourseFileRequest> nat00030CourseFileRequests(List<Client> clients, TaskListener out) {
        // Each program (course) record listed in this file must appear in either the Enrolment (NAT00120) file or
        // the Program (Qualification) Completed (NAT00130) file
        Set<NatCourse> courses = reader.enrolledAndQualificationCompletedCourses(clients);

        return nat00030CourseFileRequests(courses, out);
    }

    List<Nat00030CourseFileRequest> nat00030CourseFileRequests(Collection<NatCourse> courses, TaskListener out) {
        List<Nat00030CourseFileRequest> requests = new ArrayList<>();

        for (NatCourse course : courses) {
            try {
                Nat00030CourseFileRequest request =
                        new Nat00030CourseFileRequest(
                            course.courseIdentifier(),
                            course.courseName(),
                            course.nominalHour(),
                            course.programRecognitionIdentifier(),
                            course.levelOfEducationIdentifier(),
                            course.fieldOfEducationIdentifier(),
                            course.occupationTypeIdentifier());

                requests.add(request);
            } catch (ValidationException ex) {
                out.error(ex.getMessage());
            }
        }
        return requests;
    }

}
