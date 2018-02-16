package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TaskAssignmentFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * API for TaskAssignments. All methods are specific to a given project
 */
public interface TaskAssignmentsApi {

    /**
     *
     * @param filter
     *            filtering options
     * @param projectReference
     *            the project containing the assignments
     * @return a list of all TaskAssignments in the project, sorted by creation
     *         date, newest first.
     */
    List<TaskAssignment> list(Reference<Project> projectReference, TaskAssignmentFilter filter);

    /**
     * Return an existing TaskAssignment.
     * 
     * @param projectReference
     *            the project containing the assignment
     * @param taskAssignmentReference
     *            a reference to an existing TaskAssignment
     * @return the full TaskAssignment object
     */
    TaskAssignment get(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference);

    /**
     * Create a new TaskAssignment
     *
     * @param projectReference
     *            the project that will contain the assignment
     * @param creationInfo
     *            creation information
     * @return the created Task
     */
    TaskAssignment create(Reference<Project> projectReference, TaskAssignment creationInfo);

    /**
     * Updates the specific TaskAssignment by setting the values of the parameters
     * passed. Any parameters not provided will be left unchanged
     *
     * @param projectReference
     *            the project that contains the assignment
     * @param taskAssignmentReference
     *            An existing TaskAssignment to be updated
     * @param updateInfo
     *            the changes to be performed
     * @return the updated TaskAssignment
     */
    TaskAssignment update(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference,
            TaskAssignmentUpdateInfo updateInfo);

    /**
     * Delete an existing Task. Only possible if no time entries are associated with
     * it
     * 
     * @param projectReference
     *            the project that contains the assignment
     * @param taskAssignmentReference
     *            An existing TaskAssignment to be deleted
     */
    void delete(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference);
}
