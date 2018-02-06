package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ProjectListFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import ch.aaap.harvestclient.domain.reference.ProjectReference;

public interface ProjectsApi {

    /**
     * Return a list of projects, sorted by creation date, newest first. Use the
     * filter object to filter the list.
     * 
     * @param filter
     *            filtering options
     * @return a (filtered) list of Projects
     */
    List<Project> list(ProjectListFilter filter);

    /**
     * @param projectReference
     *            a reference to an existing Project
     * @return Return a full Project object
     */
    Project get(ProjectReference projectReference);

    /**
     * Create a new Project according to given creation information.
     * 
     * @param projectCreationInfo
     *            the creation options
     * @return the newly created Project
     */
    Project create(ProjectCreationInfo projectCreationInfo);

    /**
     * Delete an existing Project. Careful with this API! It also deletes
     * TimeEntries, Expenses and Retainers connected to this project. This will
     * generate an email notification from Harvest.
     *
     * 
     * @param projectReference
     *            a reference to an existing Project to be deleted
     */
    // TODO undocumented by Harvest, seems to work
    void delete(ProjectReference projectReference);

}
