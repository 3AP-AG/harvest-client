package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.UserUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * API for UserAssignments. All methods are specific to a given project
 *
 * @see <a href= "https://help.getharvest.com/api-v2/users-api/users/users/">
 *      Users API on Harvest</a>
 *
 */
@Api.Permission(value = Api.Role.ADMIN)
public interface UsersApi extends Api.Simple<User> {

    /**
     * Returns a list of your users. The users are returned sorted by creation date,
     * with the most recently created users appearing first. This method does not
     * filter results. Same as list(null, null);
     *
     * @return a list of all users
     */
    default List<User> list() {
        return list(null, null);
    }

    /**
     * Return a list of users, filtered by activity and update date, newest user
     * first.
     * 
     * @param isActive
     *            if true, return only getActive users. Set to null to disable
     *            filtering
     * @param updatedSince
     *            return only users that have been updated at least 1 second after
     *            updatedSince. Set to null to disable filtering.
     * @return a list of all users, filtered accordingly
     */
    List<User> list(Boolean isActive, Instant updatedSince);

    /**
     * Return a list of users, filtered by activity and update date, newest user
     * first. Page and perPage allow controlling how many results to return.
     *
     * @param isActive
     *            if true, return only getActive users. Set to null to disable
     *            filtering
     * @param updatedSince
     *            return only users that have been updated at least 1 second after
     *            updatedSince. Set to null to disable filtering.
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of users, filtered accordingly
     */
    Pagination<User> list(Boolean isActive, Instant updatedSince, int page, int perPage);

    /**
     * Create a new User. First name, last name and email are required.
     *
     * @param userCreationInfo
     *            a user creation object with the wanted properties. Null fields
     *            will get a default value according to the Harvest docs
     * @return the User that was just created
     */
    @Override
    User create(User userCreationInfo);

    /**
     * Retrieve the currently authenticated user
     *
     * @return the currently authenticated user
     */
    @Api.Permission(value = Api.Role.NONE)
    User getSelf();

    /**
     * Retrieve an existing user
     *
     * @param userReference
     *            a reference to an existing User
     * @return the full User object
     */
    @Override
    User get(Reference<User> userReference);

    /**
     * Change an existing user.
     *
     * @param user
     *            a reference to an existing User
     * @param toChange
     *            The properties to be changed. Null fields will be left as is.
     * @return the updated User
     */
    User update(Reference<User> user, UserUpdateInfo toChange);

    /**
     * Delete a user. Deleting a user is only possible if they have no time entries
     * or expenses associated with them
     *
     * @param userReference
     *            a reference to an existing User to be deleted
     */
    @Override
    void delete(Reference<User> userReference);
}
