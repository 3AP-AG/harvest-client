package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.TaskService;
import retrofit2.Call;

public class TasksApiImpl implements TasksApi {

    private static final Logger log = LoggerFactory.getLogger(TasksApiImpl.class);
    private final TaskService service;

    public TasksApiImpl(TaskService service) {
        this.service = service;
    }

    @Override
    public List<Task> list(TaskFilter filter) {
        return Common.collect((page, perPage) -> list(filter, page, perPage));
    }

    @Override
    public Pagination<Task> list(TaskFilter filter, int page, int perPage) {
        log.debug("Getting page {} of Task list", page);
        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));
        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getTasks());
    }

    @Override
    public Task get(Reference<Task> taskReference) {
        Call<Task> call = service.get(taskReference.getId());
        Task task = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", task);
        return task;
    }

    @Override
    public Task create(Task creationInfo) {
        Call<Task> call = service.create(creationInfo);
        Task task = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", task);
        return task;
    }

    @Override
    public Task update(Reference<Task> taskReference, TaskUpdateInfo toChange) {
        log.debug("Updating {} with {}", taskReference, toChange);
        Call<Task> call = service.update(taskReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Task> taskReference) {
        log.debug("Deleting {}", taskReference);
        Call<Void> call = service.delete(taskReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
