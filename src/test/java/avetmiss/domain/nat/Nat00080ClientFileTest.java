package avetmiss.domain.nat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import avetmiss.client.payload.EnrolmentInfoReadModel;
import avetmiss.client.payload.StudentReadModel;
import avetmiss.controller.payload.nat.ClientFileRequest;
import avetmiss.export.Client;
import avetmiss.util.DateUtil;
import avetmiss.util.Dates;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Nat00080ClientFileTest {
    private Nat00080ClientFile instance = new Nat00080ClientFile();

    @Test
    public void testExportRaw() throws Exception {
        Client client = createClient();
        String export = instance.export(Arrays.asList(client));
        assertThat(export, is(
                "30000     Lname, Fname                                                121996F100919813181@1201011203NYN Windsor                                           Z7ZSVMLTRG03                                                                                NOT SPECIFIED  NOT SPECIFIED                                                                             VSN123   S5\r\n"));
    }

    private Client createClient() {
        StudentReadModel student = new StudentReadModel();
        student.studentID = 30000;
        student.usi = "Z7ZSVMLTRG";
        student.vsn = "VSN123";

        student.firstName = "Fname";
        student.lastName = "Lname";

        student.enrolmentInfo = new EnrolmentInfoReadModel();
        student.enrolmentInfo.highestSchoolLevelCompletedIdentifier = "12";
        student.enrolmentInfo.yearHighestSchoolLevelCompleted = "1996";
        student.sex = "F";
        student.dob = Dates.toDate(LocalDate.of(1981, 9, 10));
        student.postCode = "3181";
        student.enrolmentInfo.indigenousStatusIdentifier = "@";
        student.enrolmentInfo.mainLanguageSpokenAtHomeIdentifier = "1201";
        student.enrolmentInfo.labourForceStatusIdentifier = "01";
        student.enrolmentInfo.countryIdentifier = "1203";
        student.enrolmentInfo.disabilityFlag = "N";
        student.enrolmentInfo.atSchoolFlag = "N";
        student.enrolmentInfo.priorEducationalAchievementFlag = "Y";
        student.enrolmentInfo.proficiencyInSpokenEnglishIdentifier = "1";
        student.enrolmentInfo.stateIdentifier = "03";

        student.suburb = "Windsor";

        // NAT00085ClientPostalDetailsFile only
        student.title = "Mr";
        student.phone = "0395234323";
        student.mobile = "04342532340";
        student.email = "user@company.com";

        return new Client(student, Collections.emptyList(), Collections.emptyList());
    }
}
