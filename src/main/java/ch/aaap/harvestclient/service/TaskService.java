package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.pagination.PaginatedTask;
import ch.aaap.harvestclient.domain.param.TaskUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface TaskService {

    String basePath = "tasks";
    String id = "taskId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedTask> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<Task> get(@Path(id) long taskId);

    @POST(basePath)
    Call<Task> create(@Body Task creationInfo);

    @PATCH(path)
    Call<Task> update(@Path(id) long taskId, @Body TaskUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long taskId);
}
