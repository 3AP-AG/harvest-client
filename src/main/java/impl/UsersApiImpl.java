package impl;

import api.UserCreationInfo;
import api.UsersApi;
import domain.User;
import domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UsersApiImpl implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiImpl.class);
    private final UserService service;

    public UsersApiImpl(UserService userService) {
        this.service = userService;
    }

    @Override
    public List<User> list() {
        Call<Users> call = service.listAll();
        try {
            Response<Users> response = call.execute();
            if (response.isSuccessful()) {
                List<User> users = response.body().getUsers();
                log.debug("Listed {} Users: {}", users.size(), users);
            }

        } catch (IOException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    @Override
    public User create(UserCreationInfo creationInfo) {
        Call<User> call = service.create(creationInfo.getOptions());

        try {
            Response<User> response = call.execute();
            return response.body();
        } catch (IOException e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public void delete(long userId) {
        Call<Void> call = service.delete(userId);
        try {
            Response<Void> response = call.execute();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override
    public void delete(User user) {
        delete(user.getId());
    }
}
