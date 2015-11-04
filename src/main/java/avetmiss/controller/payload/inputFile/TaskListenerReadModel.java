package avetmiss.controller.payload.inputFile;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class TaskListenerReadModel {
    public List<String> infoList;
    public List<String> warnList;
    public List<String> errorList;

    public TaskListenerReadModel() {
        this.infoList = newArrayList();
        this.warnList = newArrayList();
        this.errorList = newArrayList();
    }

    public TaskListenerReadModel(List<String> infoList, List<String> warnList, List<String> errorList) {
        this.infoList = infoList;
        this.warnList = warnList;
        this.errorList = errorList;
    }
}
