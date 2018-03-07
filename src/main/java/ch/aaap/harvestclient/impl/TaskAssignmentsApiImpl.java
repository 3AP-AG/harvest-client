package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.api.filter.TaskAssignmentFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.TaskAssignmentService;
import retrofit2.Call;

public class TaskAssignmentsApiImpl implements TaskAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentsApiImpl.class);
    private final TaskAssignmentService service;

    public TaskAssignmentsApiImpl(TaskAssignmentService service) {
        this.service = service;
    }

    @Override
    public List<TaskAssignment> list(Reference<Project> projectReference, TaskAssignmentFilter filter) {

        return Common.collect((page, perPage) -> list(projectReference, filter, page, perPage));
    }

    @Override
    public Pagination<TaskAssignment> list(Reference<Project> projectReference, TaskAssignmentFilter filter,
            int page, int perPage) {
        log.debug("Getting page {} of TaskAssignment list", page);

        Call<PaginatedList> call = service.list(projectReference.getId(), filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getTaskAssignments());
    }

    @Override
    public TaskAssignment get(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference) {

        Call<TaskAssignment> call = service.get(projectReference.getId(), taskAssignmentReference.getId());
        TaskAssignment taskAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Gotten {}", taskAssignment);
        return taskAssignment;
    }

    @Override
    public TaskAssignment create(Reference<Project> projectReference, TaskAssignment creationInfo) {

        Call<TaskAssignment> call = service.create(projectReference.getId(), creationInfo);
        TaskAssignment taskAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", taskAssignment);
        return taskAssignment;
    }

    @Override
    public TaskAssignment update(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference,
            TaskAssignmentUpdateInfo updateInfo) {

        Call<TaskAssignment> call = service
                .update(projectReference.getId(), taskAssignmentReference.getId(), updateInfo);
        TaskAssignment taskAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Updated {}", taskAssignment);
        return taskAssignment;
    }

    @Override
    public void delete(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference) {

        log.debug("Deleting {} in Project {}", taskAssignmentReference, projectReference);
        Call<Void> call = service.delete(projectReference.getId(), taskAssignmentReference.getId());
        ExceptionHandler.callOrThrow(call);

    }
}
