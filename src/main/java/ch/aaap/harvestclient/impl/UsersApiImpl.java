package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedUser;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.UserService;
import retrofit2.Call;

public class UsersApiImpl implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiImpl.class);

    /**
     * Default page size to request. Maximum for the API is 100.
     */
    private static final int PER_PAGE = 100;

    private final UserService service;

    public UsersApiImpl(UserService userService) {
        this.service = userService;
    }

    @Override
    public List<User> list(Boolean isActive, Instant updatedSince) {

        Integer nextPage = 1;

        List<User> users = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of user list", nextPage);
            Call<PaginatedUser> call = service.list(isActive, updatedSince, nextPage, PER_PAGE);
            PaginatedUser paginatedUser = ExceptionHandler.callOrThrow(call);
            users.addAll(paginatedUser.getUsers());
            nextPage = paginatedUser.getNextPage();
        }

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
    public User get(Reference<User> userReference) {
        Call<User> call = service.get(userReference.getId());
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public User update(Reference<User> userReference, User toChange) {

        log.debug("Updating properties {} for user {}", userReference, toChange);

        Call<User> call = service.update(userReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public void delete(Reference<User> userReference) {
        log.debug("Deleting user {}", userReference);
        Call<Void> call = service.delete(userReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
