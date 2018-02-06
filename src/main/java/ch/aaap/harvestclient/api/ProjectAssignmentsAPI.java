package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.reference.UserReference;

/**
 * Admin permissions required, except when retrieving the currently
 * authenticated user’s project assignments.
 */
public interface ProjectAssignmentsAPI {

    /**
     * List all Project Assignment for a User, sorted by creation date, newest
     * first.
     *
     * @param userReference
     *            which user to get the ProjectAssignment for
     * @return a List of all ProjectAssignment for the user
     */
    default List<ProjectAssignment> list(UserReference userReference) {
        return list(userReference, null);
    }

    /**
     * List Project Assignment for a User, sorted by creation date, newest first,
     * optionally filtered by updatedSince.
     *
     * @param updatedSince
     *            If set, return only assignments that have been updated this point
     *            in time. If null, no filtering takes place
     * @param userReference
     *            which user to get the ProjectAssignment for
     * @return a List of ProjectAssignment for the user, filtered accordingly
     */
    List<ProjectAssignment> list(UserReference userReference, Instant updatedSince);

    /**
     *
     * @return a list of all ProjectAssignment for the current user.
     */
    List<ProjectAssignment> listSelf();

}
