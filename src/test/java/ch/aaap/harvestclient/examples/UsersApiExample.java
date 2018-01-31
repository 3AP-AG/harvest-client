package ch.aaap.harvestclient.examples;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;

public class UsersApiExample {

    private static final Logger log = LoggerFactory.getLogger(UsersApiExample.class);

    private Harvest harvest = new Harvest(ConfigFactory.load("admin"));

    @Test
    public void listUsers() {

        List<User> users = harvest.users().list();

        for (User user : users) {
            log.debug("Found user: {}", user);
        }
    }

    @Test
    public void createUser() {

        UserCreationInfo userInfo = new UserCreationInfo("testFirst", "testLast", "test@test.ch");

        User newUser = harvest.users().create(userInfo);

        // cleanup
        harvest.users().delete(newUser);
    }
}
