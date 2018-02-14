package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface TasksApi {
    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all Tasks in the account, sorted by creation date, newest
     *         first.
     */
    List<Task> list(TaskFilter filter);

    /**
     * Return an existing Task.
     *
     * @param taskReference
     *            a reference to an existing Task
     * @return the full Task object
     */
    Task get(Reference<Task> taskReference);

    /**
     * Create a new Task
     *
     * @param creationInfo
     *            creation information
     * @return the created Task
     */
    Task create(Task creationInfo);

    /**
     * Updates the specific task by setting the values of the parameters passed. Any
     * parameters not provided will be left unchanged
     *
     * @param taskReference
     *            An existing Task to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated Task
     */
    Task update(Reference<Task> taskReference, TaskUpdateInfo toChange);

    /**
     * Delete an existing Task. Only possible if no time entries are associated with
     * it
     * 
     * @param taskReference
     *            a reference to the Task to be deleted
     */
    void delete(Reference<Task> taskReference);

}
