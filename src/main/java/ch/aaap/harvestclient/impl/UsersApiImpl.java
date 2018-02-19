package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.UserUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.UserService;
import retrofit2.Call;

public class UsersApiImpl implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiImpl.class);

    private final UserService service;

    public UsersApiImpl(UserService userService) {
        this.service = userService;
    }

    @Override
    public List<User> list(Boolean isActive, Instant updatedSince) {
        return Common.collect((page, perPage) -> list(isActive, updatedSince, page, perPage));
    }

    @Override
    public Pagination<User> list(Boolean isActive, Instant updatedSince, int page, int perPage) {
        log.debug("Getting page {} of user list", page);
        Call<PaginatedList> call = service.list(isActive, updatedSince, page, perPage);
        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getUsers());
    }

    @Override
    public User create(User creationInfo) {

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
    public User update(Reference<User> userReference, UserUpdateInfo toChange) {

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
