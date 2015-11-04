package avetmiss.controller.payload.inputFile;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class AvetmissInputFileProcessResult {
    public List<ClientReadModel> clients;
    public TaskListenerReadModel taskListener;

    public AvetmissInputFileProcessResult() {
        this.clients = newArrayList();
        this.taskListener = new TaskListenerReadModel();
    }

    public AvetmissInputFileProcessResult(List<ClientReadModel> clients, TaskListenerReadModel taskListener) {
        this.clients = clients;
        this.taskListener = taskListener;
    }
}
