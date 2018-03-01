package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(Api.Role.PROJECT_MANAGER)
public interface ProjectsApi extends Api.Simple<Project> {

    /**
     * Return a list of projects, sorted by creation date, newest first. Use the
     * filter object to filter the list.
     * 
     * @param filter
     *            filtering options
     * @return a (filtered) list of Projects
     */
    List<Project> list(ProjectFilter filter);

    /**
     * Return a list of projects, sorted by creation date, newest first. Use the
     * filter object to filter the list. Page and perPage allow controlling how many
     * results to return.
     *
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of Projects
     */
    Pagination<Project> list(ProjectFilter filter, int page, int perPage);

    /**
     * @param projectReference
     *            a reference to an existing Project
     * @return Return a full Project object
     */
    @Override
    Project get(Reference<Project> projectReference);

    /**
     * Create a new Project according to given creation information. Example:
     *
     * <pre>
     * Project creationInfo = ImmutableProject.builder()
     *         .client(clientReference)
     *         .name(name)
     *         .billable(billable)
     *         .billBy(billingMethod)
     *         .budgetBy(budgetBy)
     *         .build();
     * project = projectsApi.create(creationInfo);
     * </pre>
     * 
     * @param projectCreationInfo
     *            the creation options
     * @return the newly created Project
     */
    @Override
    Project create(Project projectCreationInfo);

    /**
     * Updates an existing Project with the properties set in ProjectUpdateInfo
     * 
     * @param projectReference
     *            the existing project to be updated
     * @param toChange
     *            the properties to be updated
     * @return the updated Project
     */
    Project update(Reference<Project> projectReference, ProjectUpdateInfo toChange);

    /**
     * Delete an existing Project. Careful with this API! It also deletes
     * TimeEntries, Expenses and Retainers connected to this project. This will
     * generate an email notification from Harvest.
     *
     * 
     * @param projectReference
     *            a reference to an existing Project to be deleted
     */
    @Override
    void delete(Reference<Project> projectReference);

}
