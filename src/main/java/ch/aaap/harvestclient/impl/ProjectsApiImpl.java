package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectListFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.PaginatedProject;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.ProjectReference;
import ch.aaap.harvestclient.service.ProjectService;
import retrofit2.Call;

public class ProjectsApiImpl implements ProjectsApi {

    private static final Logger log = LoggerFactory.getLogger(ProjectsApiImpl.class);
    private static final int PER_PAGE = 100;
    private final ProjectService service;

    public ProjectsApiImpl(ProjectService service) {
        this.service = service;
    }

    @Override
    public List<Project> list(ProjectListFilter filter) {
        Integer nextPage = 1;

        List<Project> projects = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of project list", nextPage);

            Map<String, Object> filterMap = filter.toMap();
            // add pagination settings
            filterMap.put("page", nextPage);
            filterMap.put("per_page", PER_PAGE);

            Call<PaginatedProject> call = service.list(filterMap);

            PaginatedProject paginatedProject = ExceptionHandler.callOrThrow(call);

            projects.addAll(paginatedProject.getProjects());
            nextPage = paginatedProject.getNextPage();
        }

        log.debug("Listed {} projects: {}", projects.size(), projects);

        return projects;
    }

    @Override
    public Project get(ProjectReference projectReference) {
        Call<Project> call = service.get(projectReference.getId());
        Project project = ExceptionHandler.callOrThrow(call);
        log.debug("Got Project {}", project);
        return project;
    }

    @Override
    public Project create(ProjectCreationInfo projectCreationInfo) {
        Call<Project> call = service.create(projectCreationInfo);
        Project project = ExceptionHandler.callOrThrow(call);
        log.debug("Created Project {}", project);
        return project;
    }

    @Override
    public Project update(ProjectReference projectReference, ProjectUpdateInfo toChange) {
        log.debug("Updating project {} with {}", projectReference, toChange);
        Call<Project> call = service.update(projectReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(ProjectReference projectReference) {

        log.debug("Deleting Project {}", projectReference);
        Call<Void> call = service.delete(projectReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
