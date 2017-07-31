package avetmiss.controller.payload.nat;

import java.util.List;

public class Nat00090DisabilityFileRequest {
    public String studentID;
    public List<String> disabilityTypeIdentifiers;

    public Nat00090DisabilityFileRequest() {}
    public Nat00090DisabilityFileRequest(String studentID, List<String> disabilityTypeIdentifiers) {
        this.studentID = studentID;
        this.disabilityTypeIdentifiers = disabilityTypeIdentifiers;
    }
}
