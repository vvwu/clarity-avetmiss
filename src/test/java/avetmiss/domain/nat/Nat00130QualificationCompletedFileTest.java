package avetmiss.domain.nat;

import java.util.Arrays;

import avetmiss.controller.payload.nat.Nat00130QualificationCompletedFileRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Nat00130QualificationCompletedFileTest {
    private Nat00130QualificationCompletedFile instance = new Nat00130QualificationCompletedFile();

    @Test
    public void testExport() {
        String export = instance.export(Arrays.asList(request()));
        assertThat(export, is("0000020829SIT50307  300020    01052016Y                                 15012015        00000                                                    \r\n"));
    }

    private Nat00130QualificationCompletedFileRequest request() {
        Nat00130QualificationCompletedFileRequest request = new Nat00130QualificationCompletedFileRequest();
        request.rtoIdentifier = "20829";
        request.courseIdentifier = "SIT50307";
        request.studentID = "300020";
        request.courseStartDate = "2015-01-15";
        request.isQualificationIssued = true;
        request.isCourseCompleted = true;
        request.dateCourseEnd = "2016-05-01";

        return request;
    }
}
