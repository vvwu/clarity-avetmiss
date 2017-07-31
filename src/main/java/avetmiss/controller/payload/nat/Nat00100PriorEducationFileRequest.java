package avetmiss.controller.payload.nat;

public class Nat00100PriorEducationFileRequest {
    public String studentID;
    public String priorEducationalAchievementIdentifier;
    public String priorEducationAchievementRecognitionIdentifier;


    public Nat00100PriorEducationFileRequest() {}
    public Nat00100PriorEducationFileRequest(String studentID, String priorEducationalAchievementIdentifier, String priorEducationAchievementRecognitionIdentifier) {
        this.studentID = studentID;
        this.priorEducationalAchievementIdentifier = priorEducationalAchievementIdentifier;
        this.priorEducationAchievementRecognitionIdentifier = priorEducationAchievementRecognitionIdentifier;
    }
}
