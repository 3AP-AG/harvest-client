package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class ExistingData {

    /**
     * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
     */
    private static class LazyHolder {
        static final ExistingData INSTANCE = new ExistingData(TestSetupUtil.getAdminAccess());
    }

    private static final Logger log = LoggerFactory.getLogger(ExistingData.class);

    private final Task task;
    private final Task anotherTask;
    private final Client client;

    private final Client anotherClient;

    private final Project project;
    private final TimeEntry timeEntry;

    private ExistingData(Harvest harvest) {
        try {
            log.debug("Initializing Existing Data for tests");
            // TODO create this objects on first run of the tests
            client = ImmutableClient.builder().id(6422922L).name("does not matter").build();
            anotherClient = ImmutableClient.builder().id(6493347L).name("does not matter").build();

            project = harvest.projects().get(new ProjectReferenceDto(16227254));
            task = harvest.tasks().get(new TaskReferenceDto(9231203));

            anotherTask = harvest.tasks().get(new TaskReferenceDto(9231203));

            Reference<TimeEntry> entry = new GenericReference<>(738720479L);
            timeEntry = harvest.timesheets().get(entry);
        } catch (Throwable t) {
            log.error("", t);
            throw t;
        }
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
