package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectAssignmentsApi;
import ch.aaap.harvestclient.api.filter.UserAssignmentFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableProject;
import ch.aaap.harvestclient.domain.Project;
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
    void listSelf(TestInfo testInfo) {

        Project project = null;
        try {
            final Project tempProject = harvest.projects().create(ImmutableProject.builder()
                    .name("Project for " + testInfo.getDisplayName())
                    .billBy(Project.BillingMethod.PROJECT)
                    .budgetBy(Project.BudgetMethod.HOURS_PER_PROJECT)
                    .billable(false)
                    .client(ExistingData.getInstance().getClientReference())
                    .build());
            project = tempProject;

            User self = harvest.users().getSelf();

            // remove self from tempProject
            harvest.userAssignments().list(project, new UserAssignmentFilter()).stream()
                    .filter(ua -> ua.getUser().getId().equals(self.getId()))
                    .forEach(ua -> harvest.userAssignments().delete(tempProject, ua));

            List<ProjectAssignment> projectAssignments = projectAssignmentsApi.listSelf();

            assertThat(projectAssignments).isNotEmpty();
            assertThat(projectAssignments).extracting("project").extracting("id").doesNotContain(tempProject.getId());
        }

        finally {
            if (project != null) {
                harvest.projects().delete(project);
            }
        }
    }
}