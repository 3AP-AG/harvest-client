package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectAssignmentService {

    @GET("users/{userId}/project_assignments")
    Call<PaginatedList> list(@Path("userId") long userId, @Query("updated_since") Instant updatedSince,
            @Query("page") int page, @Query("per_page") int perPage);

    @GET("users/me/project_assignments")
    Call<PaginatedList> list(@Query("page") int page, @Query("per_page") int perPage);

}
