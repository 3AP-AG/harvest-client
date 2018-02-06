package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedUser;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {

    @GET("users")
    Call<PaginatedUser> list(@Query("is_active") Boolean isActive, @Query("updated_since") Instant updatedSince,
            @Query("page") int page, @Query("per_page") int perPage);

    @GET("users/{userId}")
    Call<User> get(@Path("userId") long userId);

    @GET("users/me")
    Call<User> getSelf();

    @POST("users")
    Call<User> create(@Body UserCreationInfo userCreationInfo);

    @PATCH("users/{userId}")
    Call<User> update(@Path("userId") long userId, @Body User toChange);

    @DELETE("users/{userId}")
    Call<Void> delete(@Path("userId") long userId);
}
