package avetmiss.domain.nat;

import java.util.List;

import avetmiss.controller.payload.nat.Nat00060SubjectFileRequest;
import avetmiss.domain.Unit;
import avetmiss.domain.UnitRepository;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Nat00060SubjectFileTest {
    private UnitRepository unitRepository = mock(UnitRepository.class);

    private Nat00060SubjectFile instance = new Nat00060SubjectFile(unitRepository);

    @Test
    public void testExport() throws Exception {
        Unit unit = new Unit("BSBCMN407A", "Coordinate business resources", "080301");

        when(unitRepository.findByCode("BSBCMN407A")).thenReturn(unit);


        List<Nat00060SubjectFileRequest> units = newArrayList(enrolmentSubject());
        String export = instance.export(units);

        assertThat(export, is("CBSBCMN407A  COORDINATE BUSINESS RESOURCES                                                                       080301Y0100\r\n"));
    }

    public Nat00060SubjectFileRequest enrolmentSubject() {
        Nat00060SubjectFileRequest request = new Nat00060SubjectFileRequest();
        request.subjectIdentifier = "BSBCMN407A";
        request.nominalHours = 100;
        return request;
    }

}
