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
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TaskAssignmentsApiUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentsApiUpdateTest.class);

    private static final TaskAssignmentsApi taskAssignmentApi = TestSetupUtil.getAdminAccess().taskAssignments();
    private final Reference<Project> projectReference = ExistingData.getInstance().getProjectReference();
    private final Reference<Task> taskReference = ExistingData.getInstance().getUnassignedTask();

    private TaskAssignment taskAssignment;

    @AfterEach
    void afterEach() {
        if (taskAssignment != null) {
            taskAssignmentApi.delete(projectReference, taskAssignment);
        }
    }

    @Test
    void changeTask() {

        Reference<Task> anotherTaskReference = ExistingData.getInstance().getAnotherTaskReference();
        taskAssignment = taskAssignmentApi.create(projectReference, ImmutableTaskAssignment.builder()
                .taskReference(taskReference)
                .build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .task(anotherTaskReference)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(projectReference, taskAssignment, updateInfo);

        // Changing task id has been disabled by Harvest
        assertThat(updatedTaskAssignment.getTaskReference().getId()).isEqualTo(taskReference.getId());
    }

    @Test
    void changeAll() {

        taskAssignment = taskAssignmentApi.create(projectReference,
                ImmutableTaskAssignment.builder().taskReference(taskReference).build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .hourlyRate(110.)
                .budget(11111111.)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(projectReference, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment).isEqualToComparingOnlyGivenFields(updateInfo, "hourlyRate",
                "budget");
    }

    @Test
    void changeActive() {

        taskAssignment = taskAssignmentApi.create(projectReference,
                ImmutableTaskAssignment.builder().taskReference(taskReference).build());

        TaskAssignmentUpdateInfo updateInfo = ImmutableTaskAssignmentUpdateInfo.builder()
                .active(false)
                .build();
        TaskAssignment updatedTaskAssignment = taskAssignmentApi.update(projectReference, taskAssignment, updateInfo);

        assertThat(updatedTaskAssignment.getActive()).isEqualTo(updateInfo.getActive());
    }
}
