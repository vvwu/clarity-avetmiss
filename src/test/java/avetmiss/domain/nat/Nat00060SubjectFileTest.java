package avetmiss.domain.nat;

import java.util.Arrays;
import java.util.List;

import avetmiss.domain.EnrolmentSubject;
import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Nat00060SubjectFileTest {
    private Nat00060SubjectFile instance = new Nat00060SubjectFile();

    @Test
    public void testExport() throws Exception {
        List<EnrolmentSubject> units = Arrays.asList(enrolmentSubject());
        String export = instance.export(units);

        assertThat(export, is("CBSBCMN407A  COORDINATE BUSINESS RESOURCES                                                                       080301Y0100\r\n"));
    }

    public EnrolmentSubject enrolmentSubject() {
        return new EnrolmentSubject("BSBCMN407A", "Coordinate business resources","080301", 100);
    }

}
