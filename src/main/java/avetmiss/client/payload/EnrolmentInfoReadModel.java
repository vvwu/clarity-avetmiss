package avetmiss.client.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolmentInfoReadModel {
    public String stateIdentifier; // AVETMISS field
    public String countryIdentifier; // AVETMISS field
    public String countryOfBirthIdentifier;
    public String mainLanguageSpokenAtHomeIdentifier;
    public String proficiencyInSpokenEnglishIdentifier;
    public String proficiencyInReadingEnglishIdentifier;
    public String proficiencyInWritingEnglishIdentifier;
    public String indigenousStatusIdentifier;
    public String disabilityFlag;
    public String disabilityTypeIdentifiers;
    public String atSchoolFlag;
    public String highestSchoolLevelCompletedIdentifier;
    public String yearHighestSchoolLevelCompleted;
    public String priorEducationalAchievementFlag;
    public String priorEducationalAchievementIdentifier;
    public String labourForceStatusIdentifier;
    public String studyReasonIdentifier;
}
