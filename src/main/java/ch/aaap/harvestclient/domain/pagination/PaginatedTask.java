package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.Task;

public class PaginatedTask extends PaginatedList {

    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
