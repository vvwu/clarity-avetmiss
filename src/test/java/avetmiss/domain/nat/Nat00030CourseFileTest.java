package avetmiss.domain.nat;

import avetmiss.export.NatCourse;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Nat00030CourseFileTest {

    private Nat00030CourseFile instance = new Nat00030CourseFile();

    @Test
    public void testExport() throws Exception {
        Collection<NatCourse> requests = Arrays.asList(createDiplomaOfHospitality());
        String export = instance.export(requests);
        assertThat(export, is("SIT50307  DIPLOMA OF HOSPITALITY (SIT50307)                                                                   1780115141101351112Y\r\n"));
    }

    private NatCourse createDiplomaOfHospitality() {
        return new NatCourse("SIT50307", "DIPLOMA OF HOSPITALITY (SIT50307)", 1780, "11", "514","1101","351112");
    }
}
