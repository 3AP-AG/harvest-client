package examples;

import api.UserCreationInfo;
import core.Harvest;
import domain.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TestSetupUtil;

import java.util.List;

public class UsersApiExample {

    private static final Logger log = LoggerFactory.getLogger(UsersApiExample.class);

    private Harvest harvest = TestSetupUtil.getClient();


    @Test
    public void listUsers() {

        List<User> users = harvest.users().list();

        for (User user : users) {
            System.out.println(user);
        }
    }


    @Test
    public void createUser() {

        UserCreationInfo userCreationInfo = new UserCreationInfo.Builder()
                .firstName("testFirst")
                .lastName("testLast")
                .email("test@sample.ch")
                .build();

        User newUser = harvest.users().create(userCreationInfo);

        log.debug("Created User {}", newUser);
    }
}
