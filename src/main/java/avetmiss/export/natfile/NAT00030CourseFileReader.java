package avetmiss.export.natfile;

import avetmiss.export.Client;
import avetmiss.export.NatCourse;
import avetmiss.export.NatVetStudentCourse;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NAT00030CourseFileReader {

    public Set<NatCourse> enrolledAndQualificationCompletedCourses(Collection<Client> clients) {
        Set<NatCourse> courses = new LinkedHashSet<>();
        for(Client client: clients) {
            courses.addAll(client.currentEnrolmentCourses());
            courses.addAll(coursesOf(client.qualificationCompletedCourses()));
        }
        return courses;
    }

    private Set<NatCourse> coursesOf(List<NatVetStudentCourse> datas) {
        return datas.stream()
                .map(NatVetStudentCourse::natCourse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
