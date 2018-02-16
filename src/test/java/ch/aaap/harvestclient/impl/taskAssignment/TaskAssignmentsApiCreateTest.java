package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.domain.ImmutableTaskAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TaskAssignmentsApiCreateTest {

    private static final TaskAssignmentsApi taskAssignmentApi = TestSetupUtil.getAdminAccess().taskAssignments();

    private static final Project project = ExistingData.getInstance().getProject();
    private static final Task task = ExistingData.getInstance().getTask();

    private TaskAssignment taskAssignment;

    @AfterEach
    void afterEach() {
        if (taskAssignment != null) {
            taskAssignmentApi.delete(project, taskAssignment);
        }
    }

    @Test
    void createDefault() {

        TaskAssignment creationInfo = ImmutableTaskAssignment.builder().taskReference(task).build();
        taskAssignment = taskAssignmentApi.create(project, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment.getTaskReference()).isEqualToComparingOnlyGivenFields(task, "id", "name");

    }

    @Test
    void createAllOptions() {

        TaskAssignment creationInfo = ImmutableTaskAssignment.builder()
                .taskReference(task)
                // cannot create an archived task directly, it needs to have time tracked
                // against it, see
                // https://help.getharvest.com/harvest/manage/more-on-manage/managing-tasks/#archiving
                .active(true)
                .billable(false)
                .budget(2000.)
                .hourlyRate(220.)
                .build();

        taskAssignment = taskAssignmentApi.create(project, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment.getTaskReference()).isEqualToComparingOnlyGivenFields(task, "id", "name");
        assertThat(taskAssignment).isEqualToIgnoringNullFields(creationInfo);
    }
}
