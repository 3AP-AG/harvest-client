package ch.aaap.harvestclient.impl.userAssignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UserAssignmentsApi;
import ch.aaap.harvestclient.domain.ImmutableUserAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.param.ImmutableUserAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.param.UserAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class UserAssignmentsApiUpdateTest {

    private static final UserAssignmentsApi userAssignmentApi = TestSetupUtil.getAdminAccess().userAssignments();
    private final Reference<Project> projectReference = ExistingData.getInstance().getProjectReference();
    private final Reference<User> userReference = ExistingData.getInstance().getUnassignedUser();

    private UserAssignment userAssignment;

    @AfterEach
    void afterEach() {
        if (userAssignment != null) {
            userAssignmentApi.delete(projectReference, userAssignment);
        }
    }

    @Test
    void changeUser() {

        Reference<User> anotherUserReference = ExistingData.getInstance().getAnotherUserReference();
        userAssignment = userAssignmentApi.create(projectReference, ImmutableUserAssignment.builder()
                .user(userReference)
                .build());

        UserAssignmentUpdateInfo updateInfo = ImmutableUserAssignmentUpdateInfo.builder()
                .user(anotherUserReference)
                .build();
        UserAssignment updatedUserAssignment = userAssignmentApi.update(projectReference, userAssignment, updateInfo);

        // Changing user id has been disabled by Harvest
        assertThat(updatedUserAssignment.getUser().getId()).isEqualTo(userReference.getId());
    }

    @Test
    @Disabled("See https://github.com/3AP-AG/harvest-client/issues/5")
    void changeAll() {

        userAssignment = userAssignmentApi.create(projectReference,
                ImmutableUserAssignment.builder().user(userReference).build());

        UserAssignmentUpdateInfo updateInfo = ImmutableUserAssignmentUpdateInfo.builder()
                .hourlyRate(110.)
                .budget(11111111.)
                .build();
        UserAssignment updatedUserAssignment = userAssignmentApi.update(projectReference, userAssignment, updateInfo);

        assertThat(updatedUserAssignment).isEqualToComparingOnlyGivenFields(updateInfo, "hourlyRate",
                "budget");
    }

    @Test
    void changeActive() {

        userAssignment = userAssignmentApi.create(projectReference,
                ImmutableUserAssignment.builder().user(userReference).build());

        UserAssignmentUpdateInfo updateInfo = ImmutableUserAssignmentUpdateInfo.builder()
                .active(false)
                .build();
        UserAssignment updatedUserAssignment = userAssignmentApi.update(projectReference, userAssignment, updateInfo);

        assertThat(updatedUserAssignment.getActive()).isEqualTo(updateInfo.getActive());
    }
}
