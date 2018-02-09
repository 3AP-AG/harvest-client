package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ProjectAssignmentsApi;
import ch.aaap.harvestclient.domain.ProjectAssignment;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedProjectAssignment;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ProjectAssignmentService;
import retrofit2.Call;

public class ProjectAssignmentsApiImpl implements ProjectAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(ProjectAssignmentsApiImpl.class);
    private static final int PER_PAGE = 100;
    private final ProjectAssignmentService service;

    public ProjectAssignmentsApiImpl(ProjectAssignmentService service) {
        this.service = service;
    }

    @Override
    public List<ProjectAssignment> list(Reference<User> userReference, Instant updatedSince) {

        Integer nextPage = 1;

        List<ProjectAssignment> projectAssignments = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of project assigment list for user {}", nextPage, userReference);
            Call<PaginatedProjectAssignment> call = service.list(userReference.getId(), updatedSince, nextPage,
                    PER_PAGE);
            PaginatedProjectAssignment paginatedProjectAssignment = ExceptionHandler.callOrThrow(call);
            projectAssignments.addAll(paginatedProjectAssignment.getProjectAssignments());
            nextPage = paginatedProjectAssignment.getNextPage();
        }

        log.debug("Listed {} ProjectAssignment: {}", projectAssignments.size(), projectAssignments);
        return projectAssignments;
    }

    @Override
    public List<ProjectAssignment> listSelf() {
        Integer nextPage = 1;

        List<ProjectAssignment> projectAssignments = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of project assigment list for self", nextPage);
            Call<PaginatedProjectAssignment> call = service.list(nextPage, PER_PAGE);
            PaginatedProjectAssignment paginatedProjectAssignment = ExceptionHandler.callOrThrow(call);
            projectAssignments.addAll(paginatedProjectAssignment.getProjectAssignments());
            nextPage = paginatedProjectAssignment.getNextPage();
        }

        log.debug("Listed {} ProjectAssignment: {}", projectAssignments.size(), projectAssignments);
        return projectAssignments;
    }
}
