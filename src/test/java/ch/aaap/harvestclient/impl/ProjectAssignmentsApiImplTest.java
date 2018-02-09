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
import util.TestSetupUtil;

@HarvestTest
class ProjectAssignmentsApiImplTest {

    private final Harvest harvest = TestSetupUtil.getAdminAccess();

    private final ProjectAssignmentsApi projectAssignmentsApi = harvest.projectAssignments();

    private final User fixUser = harvest.users().getSelf();

    @Test
    void listUpdatedSinceNow() {

        Instant updatedSince = Instant.now();
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(fixUser, updatedSince);

        // nothing was created just now
        assertThat(projectAssignments).isEmpty();

    }

    @Test
    void listUpdatedSinceLongAgo() {

        Instant updatedSince = Instant.ofEpochSecond(0);
        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(fixUser, updatedSince);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listNoFilter() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.list(fixUser);

        assertThat(projectAssignments).isNotEmpty();

    }

    @Test
    void listSelf() {

        List<ProjectAssignment> projectAssignments = projectAssignmentsApi.listSelf();

        // TODO create from project API
        assertThat(projectAssignments).isNotEmpty();
    }
}