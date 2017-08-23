package avetmiss.controller.payload.inputFile;

import java.util.ArrayList;
import java.util.List;

public class AvetmissInputFileProcessResult {
    public List<ClientReadModel> clients;
    public TaskListenerReadModel taskListener;

    public AvetmissInputFileProcessResult() {
        this.clients = new ArrayList<>();
        this.taskListener = new TaskListenerReadModel();
    }

    public AvetmissInputFileProcessResult(List<ClientReadModel> clients, TaskListenerReadModel taskListener) {
        this.clients = clients;
        this.taskListener = taskListener;
    }
}
