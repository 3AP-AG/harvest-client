package ch.aaap.harvestclient.impl.task;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.ImmutableTask;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import util.TestSetupUtil;

@HarvestTest
class TasksApiListTest {

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
    void list() {

        List<Task> tasks = tasksApi.list(new TaskFilter());

        assertThat(tasks).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Pagination<Task> pagination = tasksApi.list(new TaskFilter(), 1, 1);

        List<Task> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByActive() {

        Task creationInfo = ImmutableTask.builder()
                .name("inactive test Task")
                .active(false)
                .build();
        task = tasksApi.create(creationInfo);

        TaskFilter filter = new TaskFilter();
        filter.setActive(false);

        List<Task> tasks = tasksApi.list(filter);

        assertThat(tasks).usingFieldByFieldElementComparator().containsExactly(task);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        Task creationInfo = ImmutableTask.builder()
                .name("newly created test Task")
                .build();
        task = tasksApi.create(creationInfo);

        TaskFilter filter = new TaskFilter();
        filter.setUpdatedSince(creationTime);

        List<Task> tasks = tasksApi.list(filter);

        assertThat(tasks).usingFieldByFieldElementComparator().containsExactly(task);

    }

}
