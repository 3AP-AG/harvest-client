package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.pagination.PaginatedTaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentCreationInfo;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface TaskAssignmentService {

    String basePath = "projects/{projectId}/task_assignments";
    String path = basePath + "/{taskAssignmentId}";

    @GET(basePath)
    Call<PaginatedTaskAssignment> list(@Path("projectId") long projectId, @QueryMap() Map<String, Object> options);

    @GET(path)
    Call<TaskAssignment> get(@Path("projectId") long projectId, @Path("taskAssignmentId") long taskAssignmentId);

    @POST(basePath)
    Call<TaskAssignment> create(@Path("projectId") long projectId, @Body TaskAssignmentCreationInfo creationInfo);

    @PATCH(path)
    Call<TaskAssignment> update(@Path("projectId") long projectId, @Path("taskAssignmentId") long taskAssignmentId,
            @Body TaskAssignmentUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path("projectId") long projectId, @Path("taskAssignmentId") long taskAssignmentId);

}
