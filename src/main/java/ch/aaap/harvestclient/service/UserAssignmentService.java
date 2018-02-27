package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.UserAssignment;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.UserAssignmentUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserAssignmentService {

    String basePath = "projects/{projectId}/user_assignments";
    String path = basePath + "/{userAssignmentId}";

    @GET(basePath)
    Call<PaginatedList> list(@Path("projectId") long projectId, @QueryMap() Map<String, Object> options);

    @GET(path)
    Call<UserAssignment> get(@Path("projectId") long projectId, @Path("userAssignmentId") long userAssignmentId);

    @POST(basePath)
    Call<UserAssignment> create(@Path("projectId") long projectId, @Body UserAssignment creationInfo);

    @PATCH(path)
    Call<UserAssignment> update(@Path("projectId") long projectId, @Path("userAssignmentId") long userAssignmentId,
            @Body UserAssignmentUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path("projectId") long projectId, @Path("userAssignmentId") long userAssignmentId);

}
