package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.Users;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.UserReference;
import ch.aaap.harvestclient.service.UserService;
import retrofit2.Call;

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

        log.debug("Creating User {}", creationInfo);

        Call<User> call = service.create(creationInfo);

        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User getSelf() {
        Call<User> call = service.getSelf();

        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User get(UserReference userReference) {
        Call<User> call = service.get(userReference.getId());
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User update(UserReference userReference, User toChange) {

        log.debug("Changing properties {} for user {}", userReference, toChange);

        Call<User> call = service.update(userReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public void delete(UserReference userReference) {
        log.debug("Deleting user {}", userReference);
        Call<Void> call = service.delete(userReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
