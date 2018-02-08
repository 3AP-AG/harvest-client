package ch.aaap.harvestclient.impl.taskAssignment;

import org.junit.jupiter.api.Test;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.domain.Project;
import util.TestSetupUtil;

@HarvestTest
public class TaskAssignmentsApiCreateTest {

    private static final TaskAssignmentsApi taskAssignmentApi = TestSetupUtil.getAdminAccess().taskAssignments();

    private static final Project project = TestSetupUtil.getExistingProject();

    @Test
    void createDefault() {

        // TODO need TasksApi
        // TaskAssignmentCreationInfo creationInfo = new TaskAssignmentCreationInfo();
        // taskAssignmentApi.create(project, creationInfo)
    }
}
