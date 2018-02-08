package util;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class ExistingData {

    /**
     * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
     */
    private static class LazyHolder {
        static final ExistingData INSTANCE = new ExistingData(TestSetupUtil.getAdminAccess());
    }

    private final Task task;
    private final Client client;
    private final Project project;
    private final TimeEntry timeEntry;

    private ExistingData(Harvest harvest) {
        client = new Client();
        client.setId(6422922L);

        project = harvest.projects().get(new ProjectReferenceDto(16227254));
        task = harvest.tasks().get(new TaskReferenceDto(9231203));

        TimeEntry entry = new TimeEntry();
        entry.setId(738720479L);
        timeEntry = harvest.timesheets().get(entry);
    }

    public static ExistingData getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Task getTask() {
        return task;
    }

    public Client getClient() {
        return client;
    }

    public Project getProject() {
        return project;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }
}
