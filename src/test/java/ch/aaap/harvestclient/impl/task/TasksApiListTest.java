package ch.aaap.harvestclient.impl.task;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.param.TaskCreationInfo;
import util.TestSetupUtil;

@HarvestTest
public class TasksApiListTest {

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
    void list() {

        List<Task> tasks = tasksApi.list(new TaskFilter());

        assertThat(tasks).isNotEmpty();

    }

    @Test
    void listByActive() {

        TaskCreationInfo creationInfo = new TaskCreationInfo("inactive test Task");
        creationInfo.setActive(false);
        task = tasksApi.create(creationInfo);

        TaskFilter filter = new TaskFilter();
        filter.setActive(false);

        List<Task> tasks = tasksApi.list(filter);

        assertThat(tasks).usingFieldByFieldElementComparator().containsExactly(task);

    }

    @Test
    void listByUpdatedSince() {

        TaskCreationInfo creationInfo = new TaskCreationInfo("newly created test Task");
        task = tasksApi.create(creationInfo);

        TaskFilter filter = new TaskFilter();
        filter.setUpdatedSince(Instant.now().minusSeconds(1));

        List<Task> tasks = tasksApi.list(filter);

        assertThat(tasks).usingFieldByFieldElementComparator().containsExactly(task);

    }

}
