package avetmiss.controller.payload.inputFile;

public class EnrolmentSubjectReadModel {
    public String subjectIdentifier;
    public String subjectName;
    public String fieldOfEducationIdentifier;
    public int nominalHours;

    public EnrolmentSubjectReadModel() {}
    public EnrolmentSubjectReadModel(String subjectIdentifier, String subjectName, String fieldOfEducationIdentifier, int nominalHours) {
        this.subjectIdentifier = subjectIdentifier;
        this.subjectName = subjectName;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
        this.nominalHours = nominalHours;
    }
}
