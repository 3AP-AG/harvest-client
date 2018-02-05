package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.UserReference;

public interface UsersApi {

    /**
     * Returns a list of your users. The users are returned sorted by creation date,
     * with the most recently created users appearing first. This method does not
     * filter results. Same as list(null, null).
     *
     * @return a list of all users
     */
    List<User> list();

    /**
     * Return a list of users, filtered by activity and update date, newest user
     * first.
     * 
     * @param isActive
     *            if true, return only active users. Set to null to disable
     *            filtering
     * @param updatedSince
     *            return only users that have been updated since updatedSince. Set
     *            to null to disable filtering
     * @return a list of all users, filtered accordingly
     */
    List<User> list(Boolean isActive, Instant updatedSince);

    /**
     * Create a new User. First name, last name and email are required.
     *
     * @param userCreationInfo
     *            a user creation object with the wanted properties. Null fields
     *            will get a default value according to the Harvest docs
     * @return the User that was just created
     */
    User create(UserCreationInfo userCreationInfo);

    /**
     * Retrieve the currently authenticated user
     *
     * @return the currently authenticated user
     */
    User getSelf();

    /**
     * Retrieve an existing user
     *
     * @param userReference
     *            a reference to an existing User
     * @return the full User object
     */
    User get(UserReference userReference);

    /**
     * Change an existing user.
     *
     * @param user
     *            a reference to an existing User
     * @param toChange
     *            A user object with the properties to be changed. Null fields will
     *            be left as is.
     * @return the updated User
     */
    User update(UserReference user, User toChange);

    /**
     * Delete a user. Deleting a user is only possible if they have no time entries
     * or expenses associated with them
     *
     * @param userReference
     *            a reference to an existing User to be deleted
     */
    void delete(UserReference userReference);
}
