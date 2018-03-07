package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.UserAssignmentsApi;
import ch.aaap.harvestclient.api.filter.UserAssignmentFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.UserAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.UserAssignmentService;
import retrofit2.Call;

public class UserAssignmentsApiImpl implements UserAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(UserAssignmentsApiImpl.class);
    private final UserAssignmentService service;

    public UserAssignmentsApiImpl(UserAssignmentService service) {
        this.service = service;
    }

    @Override
    public List<UserAssignment> list(Reference<Project> projectReference, UserAssignmentFilter filter) {

        return Common.collect((page, perPage) -> list(projectReference, filter, page, perPage));
    }

    @Override
    public Pagination<UserAssignment> list(Reference<Project> projectReference, UserAssignmentFilter filter,
            int page, int perPage) {
        log.debug("Getting page {} of UserAssignment list", page);

        Call<PaginatedList> call = service.list(projectReference.getId(), filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getUserAssignments());
    }

    @Override
    public UserAssignment get(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference) {

        Call<UserAssignment> call = service.get(projectReference.getId(), userAssignmentReference.getId());
        UserAssignment userAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Gotten {}", userAssignment);
        return userAssignment;
    }

    @Override
    public UserAssignment create(Reference<Project> projectReference, UserAssignment creationInfo) {

        Call<UserAssignment> call = service.create(projectReference.getId(), creationInfo);
        UserAssignment userAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", userAssignment);
        return userAssignment;
    }

    @Override
    public UserAssignment update(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference,
            UserAssignmentUpdateInfo updateInfo) {

        Call<UserAssignment> call = service
                .update(projectReference.getId(), userAssignmentReference.getId(), updateInfo);
        UserAssignment userAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Updated {}", userAssignment);
        return userAssignment;
    }

    @Override
    public void delete(Reference<Project> projectReference, Reference<UserAssignment> userAssignmentReference) {

        log.debug("Deleting {} in Project {}", userAssignmentReference, projectReference);
        Call<Void> call = service.delete(projectReference.getId(), userAssignmentReference.getId());
        ExceptionHandler.callOrThrow(call);

    }
}
