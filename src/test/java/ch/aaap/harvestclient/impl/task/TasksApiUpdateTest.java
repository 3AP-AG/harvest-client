package ch.aaap.harvestclient.impl.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.param.TaskCreationInfo;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import util.TestSetupUtil;

@HarvestTest
public class TasksApiUpdateTest {

    private static TasksApi tasksApi = TestSetupUtil.getAdminAccess().tasks();
    private Task task;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        task = tasksApi.create(new TaskCreationInfo("test Task for " + testInfo.getDisplayName()));
    }

    @AfterEach
    void afterEach() {
        if (task != null) {
            tasksApi.delete(task);
            task = null;
        }
    }

    @Test
    void changeName() {

        TaskUpdateInfo changes = new TaskUpdateInfo();
        changes.setName("new task name");
        Task updatedTask = tasksApi.update(task, changes);

        assertThat(updatedTask).isEqualToIgnoringNullFields(changes);

    }

    @Test
    void changeAll() {

        String name = "test Task";
        boolean billableByDefault = false;
        double defaultHourlyRate = 220.2;
        boolean defaultAddToFutureProject = true;
        boolean active = false;

        TaskUpdateInfo changes = new TaskUpdateInfo();
        changes.setName(name);
        changes.setActive(active);
        changes.setBillableByDefault(billableByDefault);
        changes.setDefaultAddToFutureProjects(defaultAddToFutureProject);
        changes.setDefaultHourlyRate(defaultHourlyRate);
        Task updatedTask = tasksApi.update(task, changes);

        assertThat(updatedTask).isEqualToIgnoringNullFields(changes);

    }

}
