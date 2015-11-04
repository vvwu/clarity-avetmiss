package avetmiss.controller.payload.inputFile;

import com.google.common.collect.Lists;

import java.util.List;

public class ClientReadModel {
    public int studentId;
    public List<EnrolmentRowReadModel> enrolments;

    public ClientReadModel() {}
    public ClientReadModel(int studentId) {
        this.studentId = studentId;
        this.enrolments = Lists.newArrayList();
    }
}
