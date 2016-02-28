package avetmiss.domain.nat;

import java.util.Arrays;

import avetmiss.controller.payload.nat.ClientFileRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Nat00085ClientPostalDetailsFileTest {
    private Nat00085ClientPostalDetailsFile instance = new Nat00085ClientPostalDetailsFile();

    @Test
    public void testExportRaw() throws Exception {
        ClientFileRequest client = createClient();
        String export = instance.export(Arrays.asList(client));
        assertThat(export, is("30000     Mr  Fname                                   Lname                           " +
                "        Building name                                     Unit                          2           " +
                "   Evelyn st                                                                                   " +
                "Windsor                                           3181030395234323                              " +
                "04342532340         user@company.com                                                                " +
                "\r\n"));
    }

    private ClientFileRequest createClient() {
        ClientFileRequest client = new ClientFileRequest();
        client.studentID = "30000";
        client.usi = "Z7ZSVMLTRG";
        client.vsn = "VSN123";

        client.firstName = "Fname";
        client.lastName = "Lname";

        client.highestSchoolLevelCompletedIdentifier = "12";
        client.yearHighestSchoolLevelCompleted = "1996";
        client.sex = "F";
        client.dateOfBirth = "1981-09-10";
        client.postCode = "3181";
        client.indigenousStatusIdentifier = "@";
        client.mainLanguageSpokenAtHomeIdentifier = "1201";
        client.labourForceStatusIdentifier = "01";
        client.countryIdentifier = "1203";
        client.disabilityFlag = "N";
        client.atSchoolFlag = "N";
        client.priorEducationalAchievementFlag = "Y";
        client.proficiencyInSpokenEnglishIdentifier = "1";
        client.suburb = "Windsor";
        client.stateIdentifier = "03";

        client.addressBuildingName = "Building name";
        client.addressFlatOrUnitDetails = "Unit";
        client.addressStreetNumber = "2";
        client.addressStreetName = "Evelyn st";

        // NAT00085ClientPostalDetailsFile only
        client.title = "Mr";
        client.contactPhoneNo = "0395234323";
        client.contactMobile = "04342532340";
        client.contactEmailAddress = "user@company.com";


        return client;
    }
}
