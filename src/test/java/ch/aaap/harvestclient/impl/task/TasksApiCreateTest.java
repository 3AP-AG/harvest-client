package ch.aaap.harvestclient.impl.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.domain.ImmutableTask;
import ch.aaap.harvestclient.domain.Task;
import util.TestSetupUtil;

@HarvestTest
class TasksApiCreateTest {

    private static final TasksApi tasksApi = TestSetupUtil.getAdminAccess().tasks();
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
        Task creationInfo = ImmutableTask.builder().name(name).build();
        task = tasksApi.create(creationInfo);

        assertThat(task.getName()).isEqualTo(name);
        Task gotten = tasksApi.get(task);
        assertThat(gotten).isEqualTo(task);
    }

    @Test
    void createAllOptions() {

        // opposites of the defaults
        String name = "test Task";
        boolean billableByDefault = false;
        double defaultHourlyRate = 220.2;
        boolean defaultAddToFutureProject = true;
        boolean active = false;

        Task creationInfo = ImmutableTask.builder()
                .name(name)
                .billableByDefault(billableByDefault)
                .defaultHourlyRate(defaultHourlyRate)
                .defaultAddToFutureProjects(defaultAddToFutureProject)
                .active(active)
                .build();

        task = tasksApi.create(creationInfo);

        assertThat(task).isEqualToIgnoringNullFields(creationInfo);
    }

}
