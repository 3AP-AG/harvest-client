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
import util.TestSetupUtil;

@HarvestTest
class ProjectAssignmentsApiImplTest {

    private Harvest harvest = TestSetupUtil.getAdminAccess();

    private ProjectAssignmentsApi projectAssignmentsApi = harvest.projectAssignments();

    private User fixUser = harvest.users().getSelf();

    @Test
    void listUpdatedSinceNow() {

        Reference<User> userReference = fixUser;
        Instant updatedSince = Instant.now();
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference, updatedSince);

        // nothing was created just now
        assertThat(projectAssignments).isEmpty();

    }

    @Test
    void listUpdatedSinceLongAgo() {

        Reference<User> userReference = fixUser;
        Instant updatedSince = Instant.ofEpochSecond(0);
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference, updatedSince);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listNoFilter() {

        Reference<User> userReference = fixUser;

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(userReference);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listSelf() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.listSelf();

        // TODO create from project API
        assertThat(projectAssignments).isNotEmpty();
    }
}