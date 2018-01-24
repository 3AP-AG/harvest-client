package service;

import domain.User;
import domain.Users;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface UserService {

    @GET("users")
    Call<Users> listAll();

    @POST("users")
    Call<User> create(@Body Map<String, Object> options);

    @DELETE("users/{userId}")
    Call<Void> delete(@Path("userId") long userId);
}
