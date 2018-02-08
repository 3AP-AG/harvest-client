package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface ProjectsApi {

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
     * @param projectReference
     *            a reference to an existing Project
     * @return Return a full Project object
     */
    Project get(Reference<Project> projectReference);

    /**
     * Create a new Project according to given creation information.
     * 
     * @param projectCreationInfo
     *            the creation options
     * @return the newly created Project
     */
    Project create(ProjectCreationInfo projectCreationInfo);

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
    void delete(Reference<Project> projectReference);

}
