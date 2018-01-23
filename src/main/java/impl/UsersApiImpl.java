package impl;

import api.UsersApi;
import domain.User;
import domain.Users;
import retrofit2.Call;
import retrofit2.Response;
import service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UsersApiImpl implements UsersApi {
    private final UserService service;

    public UsersApiImpl(UserService userService) {
        this.service = userService;
    }

    @Override
    public List<User> list() {
        Call<Users> call = service.listAll();
        try {
            Response<Users> response = call.execute();
            return response.body().getUsers();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
