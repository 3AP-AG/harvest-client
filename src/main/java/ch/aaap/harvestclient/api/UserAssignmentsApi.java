package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.UserAssignmentFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.UserAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * API for UserAssignments. All methods are specific to a given project
 */
public interface UserAssignmentsApi extends Api.GetNested<Project, UserAssignment> {

    /**
     *
     * @param filter
     *            filtering options
     * @param projectReference
     *            the project containing the assignments
     * @return a list of all UserAssignments in the project, sorted by creation
     *         date, newest first.
     */
    List<UserAssignment> list(Reference<Project> projectReference, UserAssignmentFilter filter);

    /**
     *
     * @param filter
     *            filtering options
     * @param projectReference
     *            the project containing the assignments
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all UserAssignments in the project, sorted by creation
     *         date, newest first.
     */
    Pagination<UserAssignment> list(Reference<Project> projectReference, UserAssignmentFilter filter, int page,
            int perPage);

    /**
     * Return an existing UserAssignment.
     * 
     * @param projectReference
     *            the project containing the assignment
     * @param userAssignmentReference
     *            a reference to an existing UserAssignment
     * @return the full UserAssignment object
     */
    @Override
    UserAssignment get(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference);

    /**
     * Create a new UserAssignment
     *
     * @param projectReference
     *            the project that will contain the assignment
     * @param creationInfo
     *            creation information
     * @return the created User
     */
    UserAssignment create(Reference<Project> projectReference, UserAssignment creationInfo);

    /**
     * Updates the specific UserAssignment by setting the values of the parameters
     * passed. Any parameters not provided will be left unchanged
     *
     * @param projectReference
     *            the project that contains the assignment
     * @param userAssignmentReference
     *            An existing UserAssignment to be updated
     * @param updateInfo
     *            the changes to be performed
     * @return the updated UserAssignment
     */
    UserAssignment update(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference,
            UserAssignmentUpdateInfo updateInfo);

    /**
     * Delete an existing UserAssignment.
     *
     * @param projectReference
     *            the project that contains the assignment
     * @param userAssignmentReference
     *            An existing UserAssignment to be deleted
     */
    void delete(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference);
}
