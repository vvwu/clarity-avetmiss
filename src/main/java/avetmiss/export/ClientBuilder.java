package avetmiss.export;

import avetmiss.client.payload.StudentReadModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientBuilder {
    private StudentReadModel student;
    private List<Enrolment> enrolments = new ArrayList<>();
    private List<NatVetStudentCourse> studentCourses;

    public ClientBuilder(StudentReadModel student, List<NatVetStudentCourse> studentCourses) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(studentCourses);

        this.student = student;
        this.studentCourses = studentCourses;
    }

    public ClientBuilder withEnrolment(Enrolment enrolment) {
        Objects.requireNonNull(enrolment);
        this.enrolments.add(enrolment);
        return this;
    }

    public Client build() {
        return new Client(student, enrolments, studentCourses);
    }

}
