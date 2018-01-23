package service;

import domain.Users;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("users")
    Call<Users> listAll();

}
