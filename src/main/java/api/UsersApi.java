package api;

import domain.User;

import java.util.List;

public interface UsersApi {

    /**
     * Returns a list of your users. The users are returned sorted by creation date, with the most recently created users appearing first.
     *
     * @return a list of all users
     */
    List<User> list();

    /**
     * Create a new User. First name, last name and email are required.
     *
     * @param creationInfo information about the new user.
     * @return the User that was just created
     */
    User create(UserCreationInfo creationInfo);

    void delete(long userId);

    void delete(User user);
}
