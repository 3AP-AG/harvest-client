package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentCreationInfo;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TaskAssignmentsApiUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentsApiUpdateTest.class);

    private static final TaskAssignmentsApi taskAssignmentApi = TestSetupUtil.getAdminAccess().taskAssignments();
    private final Project project = ExistingData.getInstance().getProject();
    private final Task task = ExistingData.getInstance().getTask();

    private TaskAssignment taskAssignment;

    @AfterEach
    void afterEach() {
        if (taskAssignment != null) {
            taskAssignmentApi.delete(project, taskAssignment);
        }
    }

    @Test
    void changeTask() {

        Task anotherTask = ExistingData.getInstance().getAnotherTask();
        taskAssignment = taskAssignmentApi.create(project, new TaskAssignmentCreationInfo(task));

        TaskAssignmentUpdateInfo updateInfo = new TaskAssignmentUpdateInfo();
        updateInfo.setTaskReference(anotherTask);
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getTaskReferenceDto().getId()).isEqualTo(anotherTask.getId());
    }

    @Test
    void changeAll() {

        Task anotherTask = ExistingData.getInstance().getAnotherTask();
        taskAssignment = taskAssignmentApi.create(project, new TaskAssignmentCreationInfo(task));

        TaskAssignmentUpdateInfo updateInfo = new TaskAssignmentUpdateInfo();
        updateInfo.setTaskReference(anotherTask);
        updateInfo.setHourlyRate(110.);
        updateInfo.setBudget(1111111.);
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getTaskReferenceDto().getId()).isEqualTo(anotherTask.getId());
        assertThat(updatedTaskAssignment).isEqualToComparingOnlyGivenFields(updateInfo, "hourlyRate",
                "budget");
    }

    @Test
    void changeActive() {

        taskAssignment = taskAssignmentApi.create(project, new TaskAssignmentCreationInfo(task));

        TaskAssignmentUpdateInfo updateInfo = new TaskAssignmentUpdateInfo();
        updateInfo.setActive(false);
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getActive()).isEqualTo(updateInfo.getActive());
    }
}
