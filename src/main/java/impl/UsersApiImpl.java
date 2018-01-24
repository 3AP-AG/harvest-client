package impl;

import api.UsersApi;
import domain.User;
import domain.Users;
import domain.param.UserCreationInfo;
import domain.param.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import service.UserService;

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
        Users userContainer = ExceptionHandler.callOrThrow(call);

        List<User> users = userContainer.getUsers();
        log.debug("Listed {} Users: {}", users.size(), users);
        return users;
    }

    @Override
    public User create(UserCreationInfo creationInfo) {
        Call<User> call = service.create(creationInfo.getOptions());

        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User getSelf() {
        Call<User> call = service.getSelf();

        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User get(long userId) {
        Call<User> call = service.get(userId);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User update(long userId, UserInfo userInfo) {

        Call<User> call = service.update(userId, userInfo.getOptions());
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public void delete(long userId) {
        Call<Void> call = service.delete(userId);
        ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(User user) {
        delete(user.getId());
    }
}
