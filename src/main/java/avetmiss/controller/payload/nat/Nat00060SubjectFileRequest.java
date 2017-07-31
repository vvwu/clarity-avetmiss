package avetmiss.controller.payload.nat;

public class Nat00060SubjectFileRequest {
    public String subjectIdentifier;
    public int nominalHours;

    public Nat00060SubjectFileRequest() {}
    public Nat00060SubjectFileRequest(String subjectIdentifier, int nominalHours) {
        this.subjectIdentifier = subjectIdentifier;
        this.nominalHours = nominalHours;
    }
}
