package util;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
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
    private final Task anotherTask;
    private final Client client;

    private final Client anotherClient;

    private final Project project;
    private final TimeEntry timeEntry;

    private ExistingData(Harvest harvest) {
        // TODO create this objects on first run of the tests
        client = ImmutableClient.builder().id(6422922L).build();
        anotherClient = ImmutableClient.builder().id(6493347L).build();

        project = harvest.projects().get(new ProjectReferenceDto(16227254));
        task = harvest.tasks().get(new TaskReferenceDto(9231203));

        anotherTask = harvest.tasks().get(new TaskReferenceDto(9231203));

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

    public Task getAnotherTask() {
        return anotherTask;
    }

    public Client getClient() {
        return client;
    }

    public Client getAnotherClient() {
        return anotherClient;
    }

    public Project getProject() {
        return project;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }
}
