package avetmiss.domain.nat;

import avetmiss.controller.payload.nat.ClientFileRequest;
import avetmiss.domain.AvetmissUtil;
import avetmiss.domain.ExportHelper;
import avetmiss.domain.Header;
import avetmiss.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static avetmiss.domain.Field.of;
import static avetmiss.util.StringUtil.isBlank;
import static avetmiss.util.StringUtil.isEqualToAny;
import static avetmiss.util.StringUtil.nullSafeTrimString;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
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


    public String export(List<ClientFileRequest> requests) {
        List<String[]> rows = newArrayList();

        for (ClientFileRequest client: requests) {
            String[] row = exportOneRow(client);
            rows.add(row);
        }

        return ExportHelper.writeToString(header.sizes(), rows);
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

    public String dateOfBirth(Date dob) {
        return dob == null ? "@@@@@@@@" : AvetmissUtil.toDate(dob);
    }

    public String postCode(String studentID, String aPostcode) {
        String postCode = aPostcode;
        if(isBlank(postCode)) {
            return "@@@@";
        } else if(Integer.valueOf(postCode) > 9999 || Integer.valueOf(postCode) < 1 || postCode.length() != 4) {
            System.err.println(String.format("[Export Client]: student '%s' data error: '%s' is not a valid value for 'PostCode', " +
                    "valid range is [0001, 9999]", studentID, postCode));
            return "0000"; // post code unknown
        }
        return postCode;
    }

    private String[] exportOneRow(ClientFileRequest client) {
        String studentID = client.studentID;

        String priorEducationalAchievementFlag = client.priorEducationalAchievementFlag; // prior educational achievement('@' means not specified)
        checkArgument(!"@".equals(priorEducationalAchievementFlag), "SID: %s, priorEducationalAchievementFlag = '@',  This field is now <b>mandatory</b> (@ is not valid) for all government funded and domestic fee for service enrolments that commence on or after 1/1/2010.", studentID);

        String labourForceStatusIdentifier = client.labourForceStatusIdentifier;
        String usi = requiredUsi(studentID, client.usi);

        return new String[]{
                studentID,
                nameForEncryption(client.lastName, client.firstName),
                client.highestSchoolLevelCompletedIdentifier,  // FIXME: we assume Highest School Level Completed at year 12 (Completed Year 12)
                client.yearHighestSchoolLevelCompleted,
                client.sex,
                dateOfBirth(Dates.toDateISO(client.dateOfBirth)),
                postCode(studentID, client.postCode),
                client.indigenousStatusIdentifier,
                client.mainLanguageSpokenAtHomeIdentifier,
                labourForceStatusIdentifier,
                client.countryIdentifier,
                client.disabilityFlag,
                priorEducationalAchievementFlag,
                client.atSchoolFlag,  // At School Flag ('@' means not specified)
                proficiencyInSpokenEnglishIdentifier(client),
                client.suburb,
                usi,
                client.stateIdentifier,
                client.addressBuildingName,
                client.addressFlatOrUnitDetails,
                client.addressStreetNumber,
                client.addressStreetName,
                statisticalAreaLevel1IdentifierOf(),
                statisticalAreaLevel2IdentifierOf(),
                client.vsn,
                ClientIndustryOfEmployment.clientIndustryOfEmployment(labourForceStatusIdentifier),
                ClientOccupationIdentifier.clientOccupationIdentifier(labourForceStatusIdentifier)};
    }

    public String proficiencyInSpokenEnglishIdentifier(ClientFileRequest client) {
        String mainLanguageSpokenAtHomeIdentifier = client.mainLanguageSpokenAtHomeIdentifier;

        // Leave this field blank if the Language (Main Language Other Than English Spoken at Home) Identifier field is one of the following:
        // 1201 - ENGLISH
        // 9700 - SIGN LANGUAGE
        // 9701 - AUSLAN
        // 9702 - MAKATON
        // 9799 - SIGN LANGUAGE NOT ELSEWHERE CLASSIFIED
        // or @@@@
        if(isEqualToAny(mainLanguageSpokenAtHomeIdentifier, new String[]{"1201", "9700", "9701", "9702", "9799", "@@@@"}))
            return null;

        return client.proficiencyInSpokenEnglishIdentifier;
    }

    private String requiredUsi(String studentID, String usi) {
        if(isBlank(usi)) {
            logger.warn("SID: {}, USI is missing", studentID);
            return null;
        }

        checkArgument(usi.length() == 10, "SID: %s, invalid USI: %s. USI must be 10 digits", studentID, usi);
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
