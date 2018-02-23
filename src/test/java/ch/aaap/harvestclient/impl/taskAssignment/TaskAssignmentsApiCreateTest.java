package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableTaskAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TaskAssignmentsApiCreateTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private static final TaskAssignmentsApi taskAssignmentApi = harvest.taskAssignments();

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
    void createDefault() {

        TaskAssignment creationInfo = ImmutableTaskAssignment.builder().taskReference(taskReference).build();
        taskAssignment = taskAssignmentApi.create(projectReference, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment.getTaskReference().getId()).isEqualTo(taskReference.getId());

    }

    @Test
    void createAllOptions() {

        TaskAssignment creationInfo = ImmutableTaskAssignment.builder()
                .taskReference(taskReference)
                // cannot create an archived task directly, it needs to have time tracked
                // against it, see
                // https://help.getharvest.com/harvest/manage/more-on-manage/managing-tasks/#archiving
                .active(true)
                .billable(false)
                .budget(2000.)
                .hourlyRate(220.)
                .build();

        taskAssignment = taskAssignmentApi.create(projectReference, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }
}
