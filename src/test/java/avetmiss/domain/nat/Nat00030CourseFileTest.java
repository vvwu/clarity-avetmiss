package avetmiss.domain.nat;

import java.util.List;

import avetmiss.controller.payload.nat.Nat00030CourseFileRequest;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Nat00030CourseFileTest {

    private Nat00030CourseFile instance = new Nat00030CourseFile();

    @Test
    public void testExport() throws Exception {
        List<Nat00030CourseFileRequest> requests = newArrayList(createDiplomaOfHospitality());
        String export = instance.export(requests);
        assertThat(export, is("SIT50307  DIPLOMA OF HOSPITALITY (SIT50307)                                                                   1780115141101351112Y\r\n"));
    }

    private Nat00030CourseFileRequest createDiplomaOfHospitality() {
        Nat00030CourseFileRequest request = new Nat00030CourseFileRequest();
        request.courseIdentifier = "SIT50307";
        request.courseName = "DIPLOMA OF HOSPITALITY (SIT50307)";
        request.nominalHour = 1780;
        request.programRecognitionIdentifier = "11";
        request.levelOfEducationIdentifier = "514";
        request.fieldOfEducationIdentifier = "1101";
        request.occupationTypeIdentifier = "351112";

        return request;
    }
}
