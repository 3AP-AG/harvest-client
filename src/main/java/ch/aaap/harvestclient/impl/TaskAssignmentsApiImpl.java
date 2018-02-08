package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TaskAssignmentsApi;
import ch.aaap.harvestclient.api.filter.TaskAssignmentListFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.pagination.PaginatedTaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentCreationInfo;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.TaskAssignmentService;
import retrofit2.Call;

public class TaskAssignmentsApiImpl implements TaskAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentsApiImpl.class);
    private static final int PER_PAGE = 100;
    private TaskAssignmentService service;

    public TaskAssignmentsApiImpl(TaskAssignmentService service) {
        this.service = service;
    }

    @Override
    public List<TaskAssignment> list(Reference<Project> projectReference, TaskAssignmentListFilter filter) {

        Integer nextPage = 1;

        List<TaskAssignment> result = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of TaskAssignment list", nextPage);

            Map<String, Object> filterMap = filter.toMap();
            // add pagination settings
            filterMap.put("page", nextPage);
            filterMap.put("per_page", PER_PAGE);

            Call<PaginatedTaskAssignment> call = service.list(projectReference.getId(), filterMap);

            PaginatedTaskAssignment pagination = ExceptionHandler.callOrThrow(call);

            result.addAll(pagination.getTaskAssignments());
            nextPage = pagination.getNextPage();
        }

        log.debug("Listed {} TaskAssignment: {}", result.size(), result);

        return result;
    }

    @Override
    public TaskAssignment get(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference) {

        Call<TaskAssignment> call = service.get(projectReference.getId(), taskAssignmentReference.getId());
        TaskAssignment taskAssignment = ExceptionHandler.callOrThrow(call);
        log.debug("Gotten {}", taskAssignment);
        return taskAssignment;
    }

    @Override
    public TaskAssignment create(Reference<Project> projectReference, TaskAssignmentCreationInfo creationInfo) {

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
