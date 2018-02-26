package ch.aaap.harvestclient.impl.taskAssignment;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.api.filter.TaskAssignmentFilter;
import ch.aaap.harvestclient.domain.ImmutableTaskAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TaskAssignmentsApiListTest {

    private static final TaskAssignmentsApi taskAssignmentApi = TestSetupUtil.getAdminAccess().taskAssignments();

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentsApiListTest.class);

    private static final Reference<Project> projectReference = ExistingData.getInstance().getProjectReference();
    private static final Reference<Task> taskReference = ExistingData.getInstance().getUnassignedTask();

    private TaskAssignment taskAssignment;

    @AfterEach
    void afterEach() {
        if (taskAssignment != null) {
            taskAssignmentApi.delete(projectReference, taskAssignment);
        }
    }

    @Test
    void list() {

        TaskAssignmentFilter filter = new TaskAssignmentFilter();
        List<TaskAssignment> taskAssignments = taskAssignmentApi.list(projectReference, filter);

        assertThat(taskAssignments).isNotEmpty();
    }

    @Test
    void listPaginated() {

        // create a second one to check the pagination
        TaskAssignment creationInfo = ImmutableTaskAssignment.builder()
                .taskReference(taskReference)
                .active(true)
                .build();
        taskAssignment = taskAssignmentApi.create(projectReference, creationInfo);

        Pagination<TaskAssignment> pagination = taskAssignmentApi.list(projectReference, new TaskAssignmentFilter(), 1,
                1);

        List<TaskAssignment> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByActive() {

        TaskAssignment creationInfo = ImmutableTaskAssignment.builder()
                .taskReference(taskReference)
                .active(true)
                .build();
        taskAssignment = taskAssignmentApi.create(projectReference, creationInfo);

        TaskAssignmentFilter filter = new TaskAssignmentFilter();
        filter.setActive(true);

        List<TaskAssignment> taskAssignments = taskAssignmentApi.list(projectReference, filter);

        assertThat(taskAssignments).usingFieldByFieldElementComparator().contains(taskAssignment);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        TaskAssignment creationInfo = ImmutableTaskAssignment.builder()
                .taskReference(taskReference)
                .build();
        taskAssignment = taskAssignmentApi.create(projectReference, creationInfo);
        log.debug("time now: {}", Instant.now());
        log.debug("Creation time for taskAssignment: {}", taskAssignment.getCreatedAt());
        log.debug("Update time for taskAssignment: {}", taskAssignment.getUpdatedAt());

        TaskAssignmentFilter filter = new TaskAssignmentFilter();
        filter.setUpdatedSince(creationTime);

        List<TaskAssignment> taskAssignments = taskAssignmentApi.list(projectReference, filter);

        assertThat(taskAssignments).usingFieldByFieldElementComparator().containsExactly(taskAssignment);

    }

}
