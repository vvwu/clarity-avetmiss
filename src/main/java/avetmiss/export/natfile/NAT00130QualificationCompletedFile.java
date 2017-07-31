package avetmiss.export.natfile;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import avetmiss.export.Client;
import avetmiss.export.NatVetStudentCourse;
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

    public List<Nat00130QualificationCompletedFileRequest> qualificationCompletedFile(List<Client> clients, String TOID) {
        List<Nat00130QualificationCompletedFileRequest> requests =
                clients.stream()
                        .flatMap(client -> completedFileRequestsFromOneClient(client, TOID).stream())
                        .collect(Collectors.toList());

        return requests;
    }

    private List<Nat00130QualificationCompletedFileRequest> completedFileRequestsFromOneClient(Client client, String TOID) {
        List<Nat00130QualificationCompletedFileRequest> requests = new ArrayList<>();

        List<NatVetStudentCourse> completions = client.qualificationCompletedCourses();

        if(completions.size() > 1) {
            log.info("Client sid={} has {} completions: {}", client.studentId(), completions.size(), completions);
        }

        for(NatVetStudentCourse studentCourse: completions) {
            Integer totalSupervisedHours =
                    client.totalSupervisedHours(studentCourse.getCourseIdentifier());

            try {
                requests.add(
                        toQualificationCompletedFileRequest(client.studentId(), totalSupervisedHours, studentCourse, TOID));
            } catch (Exception e) {
                throw new IllegalArgumentException(format("NAT00130QualificationCompletedFile failed at studentID: %s, studentCourseIdentifier: %s", client.studentId(), studentCourse.getCourseIdentifier()), e);
            }
        }

        return requests;
    }

    private Nat00130QualificationCompletedFileRequest toQualificationCompletedFileRequest(
            String studentID, Integer totalSupervisedHours, NatVetStudentCourse natStudentCourse, String TOID) {

        Nat00130QualificationCompletedFileRequest request = new Nat00130QualificationCompletedFileRequest();

        request.rtoIdentifier = TOID;
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
