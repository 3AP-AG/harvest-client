package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ProjectService;
import retrofit2.Call;

public class ProjectsApiImpl implements ProjectsApi {

    private static final Logger log = LoggerFactory.getLogger(ProjectsApiImpl.class);
    private final ProjectService service;

    public ProjectsApiImpl(ProjectService service) {
        this.service = service;
    }

    @Override
    public List<Project> list(ProjectFilter filter) {
        return Common.collect((page, perPage) -> list(filter, page, perPage));
    }

    @Override
    public Pagination<Project> list(ProjectFilter filter, int page, int perPage) {
        log.debug("Getting page {} of project list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getProjects());
    }

    @Override
    public Project get(Reference<Project> projectReference) {
        Call<Project> call = service.get(projectReference.getId());
        Project project = ExceptionHandler.callOrThrow(call);
        log.debug("Got Project {}", project);
        return project;
    }

    @Override
    public Project create(Project projectCreationInfo) {
        Call<Project> call = service.create(projectCreationInfo);
        Project project = ExceptionHandler.callOrThrow(call);
        log.debug("Created Project {}", project);
        return project;
    }

    @Override
    public Project update(Reference<Project> projectReference, ProjectUpdateInfo toChange) {
        log.debug("Updating project {} with {}", projectReference, toChange);
        Call<Project> call = service.update(projectReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Project> projectReference) {

        log.debug("Deleting Project {}", projectReference);
        Call<Void> call = service.delete(projectReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
