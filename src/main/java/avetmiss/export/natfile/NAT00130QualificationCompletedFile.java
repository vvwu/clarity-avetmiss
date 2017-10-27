package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import avetmiss.export.Client;
import avetmiss.export.NatVetStudentCourse;
import avetmiss.util.hudson.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static avetmiss.util.DateUtil.toISO;
import static java.lang.String.format;


/**
 * This file contains a record for each Program (Course) enrolment on the Enrolment (NAT00120) file reported during a
 * collection period, where upon completion it is expected that the client will be entitled to a Qualification or a
 * Statement of Attainment
 *
 * There may be multiple Client Identifier/ Course Identifier records on the Program (Qualification) Completions
 * files but only unique records for the Client Identifier/ Course Identifier/ Program (Course) Commencement Date
 * combination.
 */
public class NAT00130QualificationCompletedFile {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Nat00130QualificationCompletedFileRequest> qualificationCompletedFile(List<Client> clients, String TOID, TaskListener listener) {
        List<Nat00130QualificationCompletedFileRequest> requests =
                clients.stream()
                        .flatMap(client -> completedFileRequestsFromOneClient(client, TOID, listener).stream())
                        .collect(Collectors.toList());

        return requests;
    }

    private List<Nat00130QualificationCompletedFileRequest> completedFileRequestsFromOneClient(Client client, String TOID, TaskListener listener) {
        List<Nat00130QualificationCompletedFileRequest> requests = new ArrayList<>();

        List<NatVetStudentCourse> completions = client.qualificationCompletedCourses();

        if(completions.size() > 1) {
            log.info("Client sid={} has {} completions: {}", client.studentId(), completions.size(), completions);
        }

        for(NatVetStudentCourse completion: completions) {
            Integer totalSupervisedHours =
                    client.totalSupervisedHours(completion.getCourseIdentifier());

            try {
                Nat00130QualificationCompletedFileRequest request =
                        toQualificationCompletedFileRequest(client.studentId(), totalSupervisedHours, completion, TOID);

                requests.add(request);
            } catch (Exception e) {
                String error = format("NAT00130QualificationCompletedFile failed at studentID: %s, studentCourseIdentifier: %s, error: %s", client.studentId(), completion.getCourseIdentifier(), e.getMessage());
                listener.error(error);
            }
        }

        return requests;
    }

    private Nat00130QualificationCompletedFileRequest toQualificationCompletedFileRequest(
            String studentID, Integer totalSupervisedHours, NatVetStudentCourse natStudentCourse, String aRtoIdentifier) {

        Nat00130QualificationCompletedFileRequest request = new Nat00130QualificationCompletedFileRequest();

        request.rtoIdentifier = aRtoIdentifier;
        request.courseIdentifier = natStudentCourse.getCourseIdentifier();
        request.studentID = studentID;
        request.courseStartDate = toISO(natStudentCourse.courseStart());
        request.isQualificationIssued = natStudentCourse.isQualificationIssued();
        request.isCourseCompleted = natStudentCourse.isCourseCompleted();
        request.yearCourseEnd = natStudentCourse.yearCourseEnd();
        request.supervisedTeachingActivityCompletionDate = toISO(supervisedTeachingActivityCompletionDate2(natStudentCourse));
        request.supervisedHours = totalSupervisedHours;

        return request;
    }

    private Date supervisedTeachingActivityCompletionDate2(NatVetStudentCourse sc) {
        Date date = (sc.supervisedTeachingActivityCompletionDate() == null) ? sc.courseEnd() : sc.supervisedTeachingActivityCompletionDate();
        return date;
    }
}
