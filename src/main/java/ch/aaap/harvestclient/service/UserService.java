package ch.aaap.harvestclient.service;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.Users;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {

    @GET("users")
    Call<Users> listAll();

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
