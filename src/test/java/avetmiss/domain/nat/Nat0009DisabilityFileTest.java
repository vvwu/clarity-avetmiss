package avetmiss.domain.nat;

import java.util.Arrays;
import java.util.Collections;

import avetmiss.controller.payload.nat.Nat0009DisabilityFileRequest;
import avetmiss.domain.AvetmissConstant;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Nat0009DisabilityFileTest {

    private Nat0009DisabilityFile instance = new Nat0009DisabilityFile();

    @Test
    public void testExportRaw() throws Exception {
        Nat0009DisabilityFileRequest client1 = createDisabledClient();
        Nat0009DisabilityFileRequest client2 = createHealthyClient();

        String export = instance.export(asList(client1, client2));
        assertThat(export, is(
                "30000     14\r\n" +
                "30000     12\r\n" +
                "30000     99\r\n"));
    }

    private Nat0009DisabilityFileRequest createDisabledClient() {
        Nat0009DisabilityFileRequest client = new Nat0009DisabilityFileRequest();
        client.studentID = "30000";
        client.disabilityTypeIdentifiers = Arrays.asList(
                AvetmissConstant.DISABILITY_TYPE_IDENTIFIER_LEARNING,
                AvetmissConstant.DISABILITY_TYPE_IDENTIFIER_PHYSICAL,
                AvetmissConstant.DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED);

        return client;
    }

    private Nat0009DisabilityFileRequest createHealthyClient() {
        Nat0009DisabilityFileRequest client = new Nat0009DisabilityFileRequest();
        client.studentID = "30001";
        client.disabilityTypeIdentifiers = Collections.emptyList();

        return client;
    }
}
