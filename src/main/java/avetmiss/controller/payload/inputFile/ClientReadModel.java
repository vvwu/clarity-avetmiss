package avetmiss.controller.payload.inputFile;

import avetmiss.domain.EnrolmentInput;

import java.util.ArrayList;
import java.util.List;

public class ClientReadModel {
    public int studentId;
    public List<EnrolmentInput> enrolments;

    public ClientReadModel() {}
    public ClientReadModel(int studentId) {
        this.studentId = studentId;
        this.enrolments = new ArrayList<>();
    }
}
