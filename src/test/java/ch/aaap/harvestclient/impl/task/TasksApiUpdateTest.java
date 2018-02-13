package ch.aaap.harvestclient.impl.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.domain.ImmutableTask;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.param.ImmutableTaskUpdateInfo;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import util.TestSetupUtil;

@HarvestTest
class TasksApiUpdateTest {

    private static final TasksApi tasksApi = TestSetupUtil.getAdminAccess().tasks();
    private Task task;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        task = ImmutableTask.builder()
                .name("test Task for " + testInfo.getDisplayName())
                .build();
        task = tasksApi.create(task);
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
        TaskUpdateInfo changes = ImmutableTaskUpdateInfo.builder()
                .name("new task name")
                .build();
        Task updatedTask = tasksApi.update(task, changes);

        assertThat(updatedTask.getName()).isEqualTo(changes.getName());
    }

    @Test
    void changeAll() {

        String name = "test Task";
        boolean billableByDefault = false;
        double defaultHourlyRate = 220.2;
        boolean defaultAddToFutureProject = true;
        boolean active = false;

        TaskUpdateInfo changes = ImmutableTaskUpdateInfo.builder()
                .name(name)
                .active(active)
                .billableByDefault(billableByDefault)
                .defaultAddToFutureProjects(defaultAddToFutureProject)
                .defaultHourlyRate(defaultHourlyRate)
                .build();
        Task updatedTask = tasksApi.update(task, changes);

        assertThat(updatedTask).isEqualToComparingOnlyGivenFields(changes, "name", "active", "billableByDefault",
                "defaultAddToFutureProjects", "defaultHourlyRate");

    }

}
