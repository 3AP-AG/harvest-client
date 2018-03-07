package ch.aaap.harvestclient.impl.userAssignment;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UserAssignmentsApi;
import ch.aaap.harvestclient.api.filter.UserAssignmentFilter;
import ch.aaap.harvestclient.domain.ImmutableUserAssignment;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class UserAssignmentsApiListTest {

    private static final UserAssignmentsApi userAssignmentApi = TestSetupUtil.getAdminAccess().userAssignments();

    private static final Logger log = LoggerFactory.getLogger(
            UserAssignmentsApiListTest.class);

    private static final Reference<Project> projectReference = ExistingData.getInstance().getProjectReference();
    private static final Reference<User> userReference = ExistingData.getInstance().getUnassignedUser();

    private UserAssignment userAssignment;

    @AfterEach
    void afterEach() {
        if (userAssignment != null) {
            userAssignmentApi.delete(projectReference, userAssignment);
        }
    }

    @Test
    void list() {

        UserAssignmentFilter filter = new UserAssignmentFilter();
        List<UserAssignment> userAssignments = userAssignmentApi.list(projectReference, filter);

        assertThat(userAssignments).isNotEmpty();
    }

    @Test
    void listPaginated() {

        // create a second one to check the pagination
        UserAssignment creationInfo = ImmutableUserAssignment.builder()
                .user(userReference)
                .active(true)
                .build();
        userAssignment = userAssignmentApi.create(projectReference, creationInfo);

        Pagination<UserAssignment> pagination = userAssignmentApi.list(projectReference, new UserAssignmentFilter(), 1,
                1);

        List<UserAssignment> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByActive() {

        UserAssignment creationInfo = ImmutableUserAssignment.builder()
                .user(userReference)
                .active(true)
                .build();
        userAssignment = userAssignmentApi.create(projectReference, creationInfo);

        UserAssignmentFilter filter = new UserAssignmentFilter();
        filter.setActive(true);

        List<UserAssignment> userAssignments = userAssignmentApi.list(projectReference, filter);

        assertThat(userAssignments).usingFieldByFieldElementComparator().contains(userAssignment);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        UserAssignment creationInfo = ImmutableUserAssignment.builder()
                .user(userReference)
                .build();
        userAssignment = userAssignmentApi.create(projectReference, creationInfo);
        log.debug("time now: {}", Instant.now());
        log.debug("Creation time for userAssignment: {}", userAssignment.getCreatedAt());
        log.debug("Update time for userAssignment: {}", userAssignment.getUpdatedAt());

        UserAssignmentFilter filter = new UserAssignmentFilter();
        filter.setUpdatedSince(creationTime);

        List<UserAssignment> userAssignments = userAssignmentApi.list(projectReference, filter);

        assertThat(userAssignments).usingFieldByFieldElementComparator().containsExactly(userAssignment);

    }

}
