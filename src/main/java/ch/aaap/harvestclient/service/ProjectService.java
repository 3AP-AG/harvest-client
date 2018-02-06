package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.PaginatedProject;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProjectService {

    @GET("projects")
    Call<PaginatedProject> list(@QueryMap() Map<String, Object> options);

    @POST("projects")
    Call<Project> create(@Body ProjectCreationInfo projectCreationInfo);

    @DELETE("projects/{projectId}")
    Call<Void> delete(@Path("projectId") long projectId);
}
