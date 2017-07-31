package avetmiss.controller.payload.nat;

public class Nat00030CourseFileRequest {
    public String courseIdentifier;
    public String courseName;
    public int nominalHour;
    public String programRecognitionIdentifier;
    public String levelOfEducationIdentifier;
    public String fieldOfEducationIdentifier;
    public String occupationTypeIdentifier;

    public Nat00030CourseFileRequest() {}
    public Nat00030CourseFileRequest(
            String courseIdentifier,
            String courseName,
            int nominalHour,
            String programRecognitionIdentifier,
            String levelOfEducationIdentifier,
            String fieldOfEducationIdentifier,
            String occupationTypeIdentifier) {

        this.courseIdentifier = courseIdentifier;
        this.courseName = courseName;
        this.nominalHour = nominalHour;
        this.programRecognitionIdentifier = programRecognitionIdentifier;
        this.levelOfEducationIdentifier = levelOfEducationIdentifier;
        this.fieldOfEducationIdentifier = fieldOfEducationIdentifier;
        this.occupationTypeIdentifier = occupationTypeIdentifier;
    }

}
