package examples;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.Harvest;
import domain.User;
import domain.param.UserCreationInfo;
import util.TestSetupUtil;

public class UsersApiExample {

    private static final Logger log = LoggerFactory.getLogger(UsersApiExample.class);

    private Harvest harvest = TestSetupUtil.getClient();


    @Test
    public void listUsers() {

        List<User> users = harvest.users().list();

        for (User user : users) {
            log.debug("Found user: {}", user);
        }
    }

    @Test
    public void createUser() {

        UserCreationInfo userInfo = new UserCreationInfo.Builder("testFirst", "testLast", "test@test.ch").build();

        User newUser = harvest.users().create(userInfo);

        log.debug("Created User {}", newUser);

        // cleanup

        harvest.users().delete(newUser.getId());

        log.debug("Deleted User {}", newUser);
    }
}
