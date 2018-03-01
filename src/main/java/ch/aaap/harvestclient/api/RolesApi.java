package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.domain.Role;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.RoleInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Also needs Team Feature enabled
 */
@Api.Permission(Api.Role.ADMIN)
public interface RolesApi {

    /**
     * @return a list of all Roles in the account, sorted by creation date, newest
     *         first.
     */
    List<Role> list();

    /**
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all Roles in the account, sorted by creation date, newest
     *         first.
     */
    Pagination<Role> list(int page, int perPage);

    /**
     * Return an existing Role.
     * 
     * @param roleReference
     *            a reference to an existing Role
     * @return the full Role object
     */
    Role get(Reference<Role> roleReference);

    /**
     * Create a new Role
     * 
     * @param roleInfo
     *            creation information
     * @return the created Role
     */
    Role create(RoleInfo roleInfo);

    /**
     * Updates the specific role by setting the values of the parameters passed. Any
     * parameters not provided will be left unchanged
     * 
     * @param roleReference
     *            An existing Role to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated Role
     */
    Role update(Reference<Role> roleReference, RoleInfo toChange);

    /**
     * Add a role to a user
     * 
     * @param roleReference
     *            the role to be assigned
     * @param userReference
     *            the user that will be assigned the role
     * @return the updated Role
     */
    Role addUser(Reference<Role> roleReference, Reference<User> userReference);

    /**
     * Removes the given role from the given user
     *
     * @param roleReference
     *            the role to be removed
     * @param userReference
     *            the user that will lose the role
     * @return the updated Role
     */
    Role removeUser(Reference<Role> roleReference, Reference<User> userReference);

    /**
     * Delete an existing Role. Will unlink it from any users assigned to it.
     * 
     * @param roleReference
     *            a reference to the Role to be deleted
     */
    void delete(Reference<Role> roleReference);

}
