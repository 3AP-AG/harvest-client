package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectAssignmentsAPI;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.UserReference;
import util.TestSetupUtil;

@HarvestTest
class ProjectAssignmentsApiImplTest {

    private Harvest harvest = TestSetupUtil.getAdminAccess();

    private ProjectAssignmentsAPI projectAssignmentsAPI = harvest.projectAssignments();

    private User fixUser = harvest.users().getSelf();

    @Test
    void listUpdatedSinceNow() {

        UserReference userReference = fixUser;
        Instant updatedSince = Instant.now();
        List<ProjectAssignment> projectAssignments = projectAssignmentsAPI.list(userReference, updatedSince);

        // nothing was created just now
        assertThat(projectAssignments).isEmpty();

    }

    @Test
    void listUpdatedSinceLongAgo() {

        UserReference userReference = fixUser;
        Instant updatedSince = Instant.ofEpochSecond(0);
        List<ProjectAssignment> projectAssignments = projectAssignmentsAPI.list(userReference, updatedSince);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listNoFilter() {

        UserReference userReference = fixUser;

        List<ProjectAssignment> projectAssignments = projectAssignmentsAPI.list(userReference);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listSelf() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsAPI.listSelf();

        // TODO create from project API
        assertThat(projectAssignments).isNotEmpty();
    }
}