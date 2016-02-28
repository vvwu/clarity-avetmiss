package avetmiss.domain.nat;

import java.util.Arrays;

import avetmiss.controller.payload.nat.Nat00100PriorEducationFileRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Nat00100PriorEducationFileTest {
    private Nat00100PriorEducationFile instance = new Nat00100PriorEducationFile();

    @Test
    public void testExport() throws Exception {
        String export = instance.export(Arrays.asList(request()));
        assertThat(export, is("300020    420A\r\n"));
    }

    private Nat00100PriorEducationFileRequest request() {
        Nat00100PriorEducationFileRequest request = new Nat00100PriorEducationFileRequest();
        request.studentID = "300020";
        request.priorEducationalAchievementIdentifier = "420";
        request.priorEducationAchievementRecognitionIdentifier = "A";

        return request;
    }
}
