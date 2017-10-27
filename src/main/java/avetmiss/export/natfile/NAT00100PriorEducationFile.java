package avetmiss.export.natfile;


import avetmiss.controller.payload.nat.Nat00100PriorEducationFileRequest;
import avetmiss.domain.AvetmissConstant;
import avetmiss.export.Client;
import avetmiss.util.hudson.TaskListener;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

public class NAT00100PriorEducationFile {

    public List<Nat00100PriorEducationFileRequest> priorEducationFileRequests(List<Client> clients, TaskListener out) {

        List<Nat00100PriorEducationFileRequest> requests = new ArrayList<>();

        for (Client client: clients) {
            String studentID = client.studentId();
            String priorEducationalAchievementFlag = client.enrolmentInfo().priorEducationalAchievementFlag;

            ensurePriorEducationAchievementFlagIsProvided(out, studentID, priorEducationalAchievementFlag);

            if("Y".equals(priorEducationalAchievementFlag)) {
                // this client has prior educational achievement
                String priorEducationalAchievement =
                        priorEducationalAchievement(
                                out, client.enrolmentInfo().priorEducationalAchievementIdentifier, studentID);

                requests.add(new Nat00100PriorEducationFileRequest(
                        studentID,
                        priorEducationalAchievement,
                        priorEducationAchievementRecognitionIdentifier()));
            }
        }

        return requests;
    }

    private String priorEducationalAchievement(TaskListener out, String priorEducationalAchievement, String SID) {
        if(isBlank(priorEducationalAchievement)) {
            out.error("Student sid=%s priorEducationalAchievementFlag=Y but the priorEducationalAchievement detail is not provided", SID);
            priorEducationalAchievement = AvetmissConstant.PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_MISCELLANEOUSEDUCATION;
        }
        return priorEducationalAchievement;
    }

    private final static String NOT_SPECIFIED = "@";

    private void ensurePriorEducationAchievementFlagIsProvided(TaskListener out, String studentID, String priorEducationalAchievementFlag) {
        if(NOT_SPECIFIED.equals(priorEducationalAchievementFlag)) {
            out.error("studentID: %s, priorEducationalAchievementFlag = '@',  This field is now <b>mandatory</b> (@ is not valid) for all government funded and domestic fee for service enrolments that commence on or after 1/1/2010.", studentID);
        }
    }

    private final static String PRIOR_EDUCATION_ACHIEVEMENT_RECOGNITION_IDENTIFIER_AUSTRALIAN = "A";
    private final static String PRIOR_EDUCATION_ACHIEVEMENT_RECOGNITION_IDENTIFIER_AUSTRALIAN_EQUIVALENT = "E";
    private final static String PRIOR_EDUCATION_ACHIEVEMENT_RECOGNITION_IDENTIFIER_AUSTRALIAN_INTERNATIONAL = "I";

    // TODO: add to the enrolment form
    private String priorEducationAchievementRecognitionIdentifier() {
        return PRIOR_EDUCATION_ACHIEVEMENT_RECOGNITION_IDENTIFIER_AUSTRALIAN;
    }

}
