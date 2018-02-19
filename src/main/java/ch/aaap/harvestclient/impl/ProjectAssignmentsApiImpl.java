package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ProjectAssignmentsApi;
import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ProjectAssignmentService;
import retrofit2.Call;

public class ProjectAssignmentsApiImpl implements ProjectAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(ProjectAssignmentsApiImpl.class);
    private final ProjectAssignmentService service;

    public ProjectAssignmentsApiImpl(ProjectAssignmentService service) {
        this.service = service;
    }

    @Override
    public List<ProjectAssignment> list(Reference<User> userReference, Instant updatedSince) {
        return Common.collect((page, perPage) -> list(userReference, updatedSince, page, perPage));
    }

    @Override
    public Pagination<ProjectAssignment> list(Reference<User> userReference, Instant updatedSince, int page,
            int perPage) {

        log.debug("Getting page {} of project assigment list for user {}", page, userReference);
        Call<PaginatedList> call = service.list(userReference.getId(), updatedSince, page, perPage);
        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getProjectAssignments());
    }

    @Override
    public List<ProjectAssignment> listSelf() {
        return Common.collect(this::listSelf);
    }

    @Override
    public Pagination<ProjectAssignment> listSelf(int page, int perPage) {
        log.debug("Getting page {} of project assigment list for self", page);
        Call<PaginatedList> call = service.list(page, perPage);
        PaginatedList paginatedProjectAssignment = ExceptionHandler.callOrThrow(call);
        return Pagination.of(paginatedProjectAssignment, paginatedProjectAssignment.getProjectAssignments());
    }
}
