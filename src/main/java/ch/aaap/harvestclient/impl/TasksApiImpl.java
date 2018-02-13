package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TasksApi;
import ch.aaap.harvestclient.api.filter.TaskFilter;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.TaskService;
import retrofit2.Call;

public class TasksApiImpl implements TasksApi {

    private static final Logger log = LoggerFactory.getLogger(TasksApiImpl.class);
    private static final int PER_PAGE = 100;
    private final TaskService service;

    public TasksApiImpl(TaskService service) {
        this.service = service;
    }

    @Override
    public List<Task> list(TaskFilter filter) {

        Integer nextPage = 1;

        List<Task> result = new ArrayList<>();

        while (nextPage != null)

        {
            log.debug("Getting page {} of Task list", nextPage);

            Map<String, Object> filterMap = filter.toMap();
            // add pagination settings
            filterMap.put("page", nextPage);
            filterMap.put("per_page", PER_PAGE);

            Call<PaginatedList> call = service.list(filterMap);

            PaginatedList pagination = ExceptionHandler.callOrThrow(call);

            result.addAll(pagination.getTasks());
            nextPage = pagination.getNextPage();
        }

        log.debug("Listed {} Task: {}", result.size(), result);

        return result;
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
