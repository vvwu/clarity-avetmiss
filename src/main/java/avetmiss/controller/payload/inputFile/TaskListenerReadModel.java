package avetmiss.controller.payload.inputFile;

import java.util.ArrayList;
import java.util.List;

public class TaskListenerReadModel {
    public List<String> infoList;
    public List<String> warnList;
    public List<String> errorList;

    public TaskListenerReadModel() {
        this.infoList = new ArrayList<>();
        this.warnList = new ArrayList<>();
        this.errorList = new ArrayList<>();
    }

    public TaskListenerReadModel(List<String> infoList, List<String> warnList, List<String> errorList) {
        this.infoList = infoList;
        this.warnList = warnList;
        this.errorList = errorList;
    }
}
