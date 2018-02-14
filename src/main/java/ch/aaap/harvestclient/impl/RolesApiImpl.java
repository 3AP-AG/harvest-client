package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.RolesApi;
import ch.aaap.harvestclient.domain.Role;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.RoleInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.RoleService;
import retrofit2.Call;

public class RolesApiImpl implements RolesApi {

    private static final Logger log = LoggerFactory.getLogger(RolesApiImpl.class);
    private static final int PER_PAGE = 100;

    private final RoleService service;

    public RolesApiImpl(RoleService service) {
        this.service = service;
    }

    @Override
    public List<Role> list() {

        Integer nextPage = 1;

        List<Role> roles = new ArrayList<>();

        while (nextPage != null) {
            log.debug("Getting page {} of roles list", nextPage);
            Call<PaginatedList> call = service.list(nextPage, PER_PAGE);
            PaginatedList paginatedRole = ExceptionHandler.callOrThrow(call);
            roles.addAll(paginatedRole.getRoles());
            nextPage = paginatedRole.getNextPage();
        }

        log.debug("Listed {} Roles: {}", roles.size(), roles);
        return roles;
    }

    @Override
    public Role get(Reference<Role> roleReference) {
        Call<Role> call = service.get(roleReference.getId());
        Role role = ExceptionHandler.callOrThrow(call);
        log.debug("Got Role {}", role);
        return role;
    }

    @Override
    public Role create(RoleInfo roleInfo) {
        Call<Role> call = service.create(roleInfo);
        Role role = ExceptionHandler.callOrThrow(call);
        log.debug("Created Role {}", role);
        return role;
    }

    @Override
    public Role update(Reference<Role> roleReference, RoleInfo toChange) {
        log.debug("Updating Role {} with {}", roleReference, toChange);
        Call<Role> call = service.update(roleReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Role addUser(Reference<Role> roleReference, Reference<User> userReference) {

        // TODO PERF could be optimized if we already receive a Role
        Role role = get(roleReference);

        RoleInfo roleInfo = new RoleInfo(role.getName());
        List<Long> userIds = role.getUserIds().stream().map(Reference::getId).collect(Collectors.toList());
        userIds.add(userReference.getId());
        roleInfo.setUserIds(userIds);

        return update(roleReference, roleInfo);

    }

    @Override
    public Role removeUser(Reference<Role> roleReference, Reference<User> userReference) {

        // TODO PERF could be optimized if we already receive a Role
        Role role = get(roleReference);

        RoleInfo roleInfo = new RoleInfo(role.getName());
        List<Long> userIds = role.getUserIds().stream().map(Reference::getId).collect(Collectors.toList());
        userIds.remove(userReference.getId());
        roleInfo.setUserIds(userIds);

        return update(roleReference, roleInfo);
    }

    @Override
    public void delete(Reference<Role> roleReference) {
        log.debug("Deleting role {}", roleReference);
        Call<Void> call = service.delete(roleReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
