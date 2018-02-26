package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectAssignmentsApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ProjectAssignmentsApiImplTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private final ProjectAssignmentsApi projectAssignmentsApi = harvest.projectAssignments();

    private final Reference<User> userReference = ExistingData.getInstance().getUserReference();

    private final ProjectAssignment projectAssignment = ExistingData.getInstance().getProjectAssignment();

    @Test
    void listUpdatedSinceNow() {

        Instant updatedSince = Instant.now();
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference, updatedSince);

        // nothing was created just now
        assertThat(projectAssignments).isEmpty();

    }

    @Test
    void listUpdatedSinceLongAgo() {

        Instant updatedSince = Instant.ofEpochSecond(0);
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference, updatedSince);

        assertThat(projectAssignments).contains(projectAssignment);

    }

    @Test
    void listNoFilter() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference);

        assertThat(projectAssignments).contains(projectAssignment);

    }

    @Test
    void listSelf() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.listSelf();

        // TODO add a project we are not on, check for that missing here
        // could use the UserAssignment API

        assertThat(projectAssignments).isNotEmpty();
    }
}