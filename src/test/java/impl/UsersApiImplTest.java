package impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

import api.UsersApi;
import domain.User;
import domain.param.UserCreationInfo;
import domain.param.UserInfo;
import exception.NotFoundException;
import exception.RequestProcessingException;
import util.TestSetupUtil;

class UsersApiImplTest {

    private final static Logger log = LoggerFactory.getLogger(UsersApiImplTest.class);

    private static UsersApi api = TestSetupUtil.getClient().users();

    // user created and deleted in every test
    private final static String userFirst = "First";
    private final static String userLast = "Last";
    private final static String userEmail = "test@test.com";

    // user that is assumed to exist already
    private final static String fixUserFirst = "FixFirst";
    private final static String fixUserLast = "FixLast";
    private final static String fixUserEmail = "fix.user@gmail.com";
    private static User fixUser;

    @BeforeAll
    public static void beforeAll() {

        List<User> users = api.list();

        Optional<User> user = users.stream().filter(u -> u.getEmail().equals(fixUserEmail)).findFirst();
        if (user.isPresent()) {
            log.debug("Fix user exists already, nothing to do");
            fixUser = user.get();
        } else {
            UserCreationInfo creationInfo = new UserCreationInfo.Builder(fixUserFirst, fixUserLast, fixUserEmail)
                    .build();
            fixUser = api.create(creationInfo);
            log.debug("Created Fix user");
        }
    }

    @Test
    void list() {

        List<User> users = api.list();

        assertTrue(users.size() > 0);
    }

    @Test
    void createExistingEmailFails() {
        RequestProcessingException exception = Assertions.assertThrows(RequestProcessingException.class, () -> {
            UserCreationInfo creationInfo = new UserCreationInfo.Builder(fixUserFirst, fixUserLast, fixUserEmail)
                    .build();

            api.create(creationInfo);
        });
        assertEquals(422, exception.getHttpCode());
        assertTrue(exception.getMessage().contains(fixUserEmail));
    }

    @Test
    void createAndDeleteUser() {

        UserCreationInfo creationInfo = new UserCreationInfo.Builder(userFirst, userLast, userEmail).build();

        User user = api.create(creationInfo);

        // cleanup
        api.delete(user);
    }

    @Test
    void createAndDeleteUserById() {

        UserCreationInfo creationInfo = new UserCreationInfo.Builder(userFirst, userLast, userEmail).build();

        User user = api.create(creationInfo);

        // cleanup
        api.delete(user.getId());
    }

    @Test
    void getSelf() {

        // TODO this will fail for another authenticated user
        User self = api.getSelf();

        assertEquals("Marco", self.getFirstName());
        assertEquals("marco.nembrini.co@gmail.com", self.getEmail());
    }

    @Test
    void getUser() {
        User test = api.get(fixUser.getId());

        assertEquals(fixUserFirst, test.getFirstName());
    }

    @Test
    void getUserNotExisting() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> api.get(1));
        assertEquals(404, e.getHttpCode());
    }

    @Test
    void testChangeEmail() {

        String email = "new@email.org";
        long userId = fixUser.getId();

        UserInfo userInfo = new UserInfo.Builder().email(email).build();

        User user = api.update(userId, userInfo);

        assertEquals(email, user.getEmail());

        // restore email

        api.update(userId, new UserInfo.Builder().email(fixUserEmail).build());
    }
}