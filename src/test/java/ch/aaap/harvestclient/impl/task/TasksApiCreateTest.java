package ch.aaap.harvestclient.impl.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.param.TaskCreationInfo;
import util.TestSetupUtil;

@HarvestTest
public class TasksApiCreateTest {

    private static TasksApi tasksApi = TestSetupUtil.getAdminAccess().tasks();
    private Task task;

    @AfterEach
    void afterEach() {
        if (task != null) {
            tasksApi.delete(task);
            task = null;
        }
    }

    @Test
    void create() {

        String name = "test Task";
        TaskCreationInfo creationInfo = new TaskCreationInfo(name);
        task = tasksApi.create(creationInfo);

        assertThat(task.getName()).isEqualTo(name);
    }

    @Test
    void createAllOptions() {

        // opposites of the defaults
        String name = "test Task";
        boolean billableByDefault = false;
        double defaultHourlyRate = 220.2;
        boolean defaultAddToFutureProject = true;
        boolean active = false;

        TaskCreationInfo creationInfo = new TaskCreationInfo(name);
        creationInfo.setBillableByDefault(billableByDefault);
        creationInfo.setDefaultHourlyRate(defaultHourlyRate);
        creationInfo.setDefaultAddToFutureProjects(defaultAddToFutureProject);
        creationInfo.setActive(active);

        task = tasksApi.create(creationInfo);

        assertThat(task).isEqualToIgnoringNullFields(creationInfo);
    }

}
