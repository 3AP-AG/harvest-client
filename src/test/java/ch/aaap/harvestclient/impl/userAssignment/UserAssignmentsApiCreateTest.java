package ch.aaap.harvestclient.impl.userAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UserAssignmentsApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableUserAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class UserAssignmentsApiCreateTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private static final UserAssignmentsApi userAssignmentApi = harvest.userAssignments();

    private static final Reference<Project> projectReference = ExistingData.getInstance().getProjectReference();
    private static final Reference<User> userReference = ExistingData.getInstance().getUserReference();

    private UserAssignment userAssignment;

    @AfterEach
    void afterEach() {
        if (userAssignment != null) {
            userAssignmentApi.delete(projectReference, userAssignment);
        }
    }

    @Test
    void createDefault() {

        UserAssignment creationInfo = ImmutableUserAssignment.builder().user(userReference).build();
        userAssignment = userAssignmentApi.create(projectReference, creationInfo);

        assertThat(userAssignment).isNotNull();
        assertThat(userAssignment.getUser().getId()).isEqualTo(userReference.getId());

    }

    @Test
    void createAllOptions() {

        UserAssignment creationInfo = ImmutableUserAssignment.builder()
                .user(userReference)
                .active(false)
                .hourlyRate(220.)
                .budget(1111.)
                .projectManager(true)
                .build();

        userAssignment = userAssignmentApi.create(projectReference, creationInfo);

        assertThat(userAssignment).isNotNull();
        assertThat(userAssignment).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }
}
