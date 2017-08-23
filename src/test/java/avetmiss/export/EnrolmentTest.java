package avetmiss.export;

import avetmiss.domain.EnrolmentInput;
import avetmiss.util.Dates;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static java.time.LocalDate.now;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnrolmentTest {

    private Enrolment enrolment;

    private int studentID = 30013;
    private NatVetStudentCourse natStudentCourse;
    private String unitCode = "SWECAS202A";

    @Before
    public void setUp() throws Exception {
        this.natStudentCourse = mock(NatVetStudentCourse.class);

        this.enrolment = new Enrolment();

        EnrolmentInput enrolmentInput = new EnrolmentInput();
        enrolmentInput.setStudentId(studentID);
        enrolmentInput.setUnitCode(unitCode);

        this.enrolment.setEnrolmentInput(enrolmentInput);
        this.enrolment.setNatStudentCourse(natStudentCourse);
    }

    @Test
    public void testEnrolmentIdentifier() throws Exception {
        when(natStudentCourse.uniqueIdentifier()).thenReturn("9999999");
        assertThat(enrolment.simpleEnrolmentIdentifier(), is("s30013-c9999999-uSWECAS202A"));
    }

    @Test
    public void commencingCourseIdentifier() throws Exception {
        Date today = new Date();

        when(natStudentCourse.courseStart()).thenReturn(today);
        assertThat("for the first time, the value must be 3",
                enrolment.commencingCourseIdentifier(), is("3"));

        java.time.LocalDate todayLastYear = now().minusYears(1);
        when(natStudentCourse.courseStart()).thenReturn(Dates.toDate(todayLastYear));
        assertThat("in a previous collection year and has not completed the qualification, the value must be 4",
                enrolment.commencingCourseIdentifier(), is("4"));
    }
}