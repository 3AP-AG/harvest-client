package service;

import domain.User;
import domain.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.Map;

public interface UserService {

    @GET("users")
    Call<Users> listAll();

    @POST("users")
    Call<User> create(@Body Map<String, Object> options);
}
