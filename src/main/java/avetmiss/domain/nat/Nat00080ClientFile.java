package avetmiss.domain.nat;

import avetmiss.client.payload.EnrolmentInfoReadModel;
import avetmiss.domain.*;
import avetmiss.export.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.util.StringUtil.*;
import static java.lang.String.format;

public class Nat00080ClientFile {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Header header = Header.Header(362,
            of("Client (Student) Identifier", 10),
            of("Name for Encryption", 60),
            of("Highest School Level Completed", 2),
            of("Year Highest School Level Completed", 4),
            of("Sex", 1),
            of("Date of Birth", 8),
            of("Postcode", 4),
            of("Indigenous Status Identifier", 1),
            of("Main Language Other Than English Spoken at Home Identifier", 4),
            of("Labour Force Status Identifier", 2),
            of("Country Identifier", 4),
            of("Disability Flag", 1),
            of("Prior Educational Achievement Flag", 1),
            of("At School Flag", 1),
            of("Proficiency in Spoken English Identifier", 1),
            of("Address Location - Suburb, Locality or Town", 50),
            of("Unique Student Identifier", 10),
            of("State Identifier", 2),
            of("Address Building/Property Name", 50),
            of("Address Flat/Unit Details", 30),
            of("Address Street Number", 15),
            of("Address Street Name", 70),
            of("Statistical Area Level 1 Identifier", 11),
            of("Statistical Area Level 2 Identifier", 9),
            of("Victorian Student Number (VSN)", 9),
            of("Client Industry of Employment", 1),
            of("Client Occupation Identifier", 1));


    public String export(List<Client> requests) {
        List<Row> rows = new ArrayList();
        for (Client client: requests) {
            Row row = exportOneRow(client);
            rows.add(row);
        }

        return ExportHelper.writeToString(header, rows);
    }

    /**
     * AVETMISS field: Name for Encryption must be recorded in the following
     * order: family name (comma) (space) first given name (space) second given
     * name.
     */
    public String nameForEncryption(String lastName, String firstName) {
        return format("%s, %s",
                nullSafeTrimString(lastName),
                nullSafeTrimString(firstName));
    }

    public String dateOfBirth(LocalDate dob) {
        return dob == null ? "@@@@@@@@" : AvetmissUtil.toDate(dob);
    }

    private Row exportOneRow(Client client) {
        String studentID = client.studentId();
        EnrolmentInfoReadModel enrolmentInfo = client.enrolmentInfo();

        String priorEducationalAchievementFlag = enrolmentInfo.priorEducationalAchievementFlag; // prior educational achievement('@' means not specified)

        if ("@".equals(priorEducationalAchievementFlag)) {
            logger.error("SID: {}, priorEducationalAchievementFlag = '@',  This field is now <b>mandatory</b> (@ is not valid) for all government funded and domestic fee for service enrolments that commence on or after 1/1/2010.", studentID);
        }

        String labourForceStatusIdentifier = enrolmentInfo.labourForceStatusIdentifier;
        String usi = requiredUsi(studentID, client.usi());

        return new Row(
                studentID,
                nameForEncryption(client.lastName(), client.firstName()),
                enrolmentInfo.highestSchoolLevelCompletedIdentifier,  // FIXME: we assume Highest School Level Completed at year 12 (Completed Year 12)
                enrolmentInfo.yearHighestSchoolLevelCompleted,
                client.sex(),
                dateOfBirth(client.dob()),
                StudentPostCode.postCode(studentID, client.getPostCode()),
                enrolmentInfo.indigenousStatusIdentifier,
                enrolmentInfo.mainLanguageSpokenAtHomeIdentifier,
                labourForceStatusIdentifier,
                enrolmentInfo.countryIdentifier,
                enrolmentInfo.disabilityFlag,
                priorEducationalAchievementFlag,
                enrolmentInfo.atSchoolFlag,  // At School Flag ('@' means not specified)
                proficiencyInSpokenEnglishIdentifier(enrolmentInfo),
                client.suburb(),
                usi,
                enrolmentInfo.stateIdentifier,
                client.address().addressBuildingName(),
                client.address().addressFlatOrUnitDetails(),
                client.address().addressStreetNumber(),
                client.address().addressStreetName(),
                statisticalAreaLevel1IdentifierOf(),
                statisticalAreaLevel2IdentifierOf(),
                client.vsn(),
                ClientIndustryOfEmployment.clientIndustryOfEmployment(labourForceStatusIdentifier),
                ClientOccupationIdentifier.clientOccupationIdentifier(labourForceStatusIdentifier));
    }

    public String proficiencyInSpokenEnglishIdentifier(EnrolmentInfoReadModel enrolmentInfo) {
        String mainLanguageSpokenAtHomeIdentifier = enrolmentInfo.mainLanguageSpokenAtHomeIdentifier;

        // Leave this field blank if the Language (Main Language Other Than English Spoken at Home) Identifier field is one of the following:
        // 1201 - ENGLISH
        // 9700 - SIGN LANGUAGE
        // 9701 - AUSLAN
        // 9702 - MAKATON
        // 9799 - SIGN LANGUAGE NOT ELSEWHERE CLASSIFIED
        // or @@@@
        if(isEqualToAny(mainLanguageSpokenAtHomeIdentifier, new String[]{"1201", "9700", "9701", "9702", "9799", "@@@@"}))
            return null;

        return enrolmentInfo.proficiencyInSpokenEnglishIdentifier;
    }

    private String requiredUsi(String studentID, String usi) {
        if(isBlank(usi)) {
            logger.warn("SID: {}, USI is missing", studentID);
            return null;
        }

        return usi;
    }

    private String statisticalAreaLevel1IdentifierOf() {
        // This field must be blank.
        return null;
    }
    private String statisticalAreaLevel2IdentifierOf() {
        return null;
    }
}
