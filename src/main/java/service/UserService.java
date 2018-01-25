package service;

import java.util.Map;

import domain.User;
import domain.Users;
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
    Call<User> create(@Body Map<String, Object> options);

    @PATCH("users/{userId}")
    Call<User> update(@Path("userId") long userId, @Body Map<String, Object> options);

    @DELETE("users/{userId}")
    Call<Void> delete(@Path("userId") long userId);
}
