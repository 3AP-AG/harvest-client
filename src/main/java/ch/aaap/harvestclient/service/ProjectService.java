package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProjectService {

    @GET("projects")
    Call<PaginatedList> list(@QueryMap() Map<String, Object> options);

    @GET("projects/{projectId}")
    Call<Project> get(@Path("projectId") long projectId);

    @POST("projects")
    Call<Project> create(@Body Project projectCreationInfo);

    @PATCH("projects/{projectId}")
    Call<Project> update(@Path("projectId") long projectId, @Body ProjectUpdateInfo projectUpdateInfo);

    @DELETE("projects/{projectId}")
    Call<Void> delete(@Path("projectId") long projectId);
}
