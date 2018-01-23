package api;

import domain.User;

import java.util.List;

public interface UsersApi {

    List<User> list();

    User create(UserCreationInfo options);
}
