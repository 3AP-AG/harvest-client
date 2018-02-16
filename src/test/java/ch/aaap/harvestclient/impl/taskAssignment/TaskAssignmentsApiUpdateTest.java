package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.domain.ImmutableTaskAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.param.ImmutableTaskAssignmentUpdateInfo;
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
        taskAssignment = taskAssignmentApi.create(project,
                ImmutableTaskAssignment.builder().taskReference(task).build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .task(anotherTask)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getTaskReference().getId()).isEqualTo(anotherTask.getId());
    }

    @Test
    void changeAll() {

        Task anotherTask = ExistingData.getInstance().getAnotherTask();
        taskAssignment = taskAssignmentApi.create(project,
                ImmutableTaskAssignment.builder().taskReference(task).build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .task(anotherTask)
                .hourlyRate(110.)
                .budget(11111111.)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getTaskReference().getId()).isEqualTo(anotherTask.getId());
        assertThat(updatedTaskAssignment).isEqualToComparingOnlyGivenFields(updateInfo, "hourlyRate",
                "budget");
    }

    @Test
    void changeActive() {

        taskAssignment = taskAssignmentApi.create(project,
                ImmutableTaskAssignment.builder().taskReference(task).build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .active(false)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(project, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getActive()).isEqualTo(updateInfo.getActive());
    }
}
