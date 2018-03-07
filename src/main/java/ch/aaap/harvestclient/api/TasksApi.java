package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * @see <a href= "https://help.getharvest.com/api-v2/tasks-api/tasks/tasks/">
 *      Tasks API on Harvest</a>
 *
 */
@Api.Permission(Api.Role.ADMIN)
public interface TasksApi extends Api.Simple<Task> {
    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all Tasks in the account, sorted by creation date, newest
     *         first.
     */
    List<Task> list(TaskFilter filter);

    /**
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all Tasks in the account, sorted by creation date, newest
     *         first.
     */
    Pagination<Task> list(TaskFilter filter, int page, int perPage);

    /**
     * Return an existing Task.
     *
     * @param taskReference
     *            a reference to an existing Task
     * @return the full Task object
     */
    @Override
    Task get(Reference<Task> taskReference);

    /**
     * Create a new Task. Example:
     * 
     * <pre>
     * Task task = harvest.tasks().create(ImmutableTask.builder()
     *         .name("task name")
     *         .build());
     * </pre>
     *
     * @param creationInfo
     *            creation information
     * @return the created Task
     */
    @Override
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
    @Override
    void delete(Reference<Task> taskReference);

}
