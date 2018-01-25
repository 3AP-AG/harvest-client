package api;

import java.util.List;

import domain.User;
import domain.param.UserCreationInfo;
import domain.param.UserInfo;

public interface UsersApi {

    /**
     * Returns a list of your users. The users are returned sorted by creation date,
     * with the most recently created users appearing first.
     *
     * @return a list of all users
     */
    List<User> list();

    /**
     * Create a new User. First name, last name and email are required.
     *
     * @param creationInfo
     *            information about the new user.
     * @return the User that was just created
     */
    User create(UserCreationInfo creationInfo);

    /**
     * Retrieve the currently authenticated user
     *
     * @return the currently authenticated user
     */
    User getSelf();

    /**
     * Retrieve an existing user
     *
     * @param userId
     *            the id of an existing user
     * @return the full User object
     */
    User get(long userId);

    /**
     * Change an existing user.
     *
     * @param userId
     *            the id of the user to be changed
     * @param userInfo
     *            contains all the properties to be changed
     * @return the updated User
     */
    User update(long userId, UserInfo userInfo);

    /**
     * Delete a user. Deleting a user is only possible if they have no time entries
     * or expenses associated with them
     *
     * @param userId
     *            the id of the user to be deleted
     */
    void delete(long userId);

    /**
     * Delete a user. Deleting a user is only possible if they have no time entries
     * or expenses associated with them
     *
     * @param user
     *            the user to be deleted
     */
    void delete(User user);
}
