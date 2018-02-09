package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentCreationInfo;
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

        TaskAssignmentCreationInfo creationInfo = new TaskAssignmentCreationInfo(task);
        taskAssignment = taskAssignmentApi.create(project, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment.getTaskReferenceDto()).isEqualToComparingOnlyGivenFields(task, "id", "name");

    }

    @Test
    void createAllOptions() {

        TaskAssignmentCreationInfo creationInfo = new TaskAssignmentCreationInfo(task);

        // cannot create an archived task directly, it needs to have time tracked
        // against it, see
        // https://help.getharvest.com/harvest/manage/more-on-manage/managing-tasks/#archiving
        creationInfo.setActive(true);
        creationInfo.setBillable(false);
        creationInfo.setBudget(2000.);
        creationInfo.setHourlyRate(220.);
        taskAssignment = taskAssignmentApi.create(project, creationInfo);

        assertThat(taskAssignment).isNotNull();
        assertThat(taskAssignment.getTaskReferenceDto()).isEqualToComparingOnlyGivenFields(task, "id", "name");
        assertThat(taskAssignment).isEqualToIgnoringNullFields(creationInfo);
    }
}
