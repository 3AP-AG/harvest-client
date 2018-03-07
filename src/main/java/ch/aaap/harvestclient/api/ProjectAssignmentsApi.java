package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Admin permissions required, except when retrieving the currently
 * authenticated user’s project assignments.
 * 
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/users-api/users/project-assignments/">
 *      User Project Assignments API on Harvest</a>
 */
@Api.Permission(value = Api.Role.ADMIN, onlySelf = true)
public interface ProjectAssignmentsApi {

    /**
     * List all Project Assignment for a User, sorted by creation date, newest
     * first.
     *
     * @param userReference
     *            which user to get the ProjectAssignment for
     * @return a List of all ProjectAssignment for the user
     */
    default List<ProjectAssignment> list(Reference<User> userReference) {
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
    List<ProjectAssignment> list(Reference<User> userReference, Instant updatedSince);

    /**
     * List Project Assignment for a User, sorted by creation date, newest first,
     * optionally filtered by updatedSince. Page and perPage allow controlling how
     * many results to return.
     *
     * @param updatedSince
     *            If set, return only assignments that have been updated this point
     *            in time. If null, no filtering takes place
     * @param userReference
     *            which user to get the ProjectAssignment for
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a List of ProjectAssignment for the user, filtered accordingly
     */
    Pagination<ProjectAssignment> list(Reference<User> userReference, Instant updatedSince, int page, int perPage);

    /**
     *
     * @return a list of all ProjectAssignment for the current user.
     */
    List<ProjectAssignment> listSelf();

    /**
     * Return a list of all ProjectAssignment for the current user. Page and perPage
     * allow controlling how many results to return.
     * 
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all ProjectAssignment for the current user.
     */
    Pagination<ProjectAssignment> listSelf(int page, int perPage);

}
