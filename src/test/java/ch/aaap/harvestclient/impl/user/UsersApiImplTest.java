package ch.aaap.harvestclient.impl.user;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;
import ch.aaap.harvestclient.exception.NotFoundException;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.TestSetupUtil;

class UsersApiImplTest {

    private final static Logger log = LoggerFactory.getLogger(UsersApiImplTest.class);
    // user that is assumed to exist already
    private final static String fixUserFirst = "FixFirst";
    private final static String fixUserLast = "FixLast";
    private final static String fixUserEmail = "fix.user@example.com";
    private static UsersApi api = TestSetupUtil.getAdminAccess().users();
    private static User fixUser;

    @BeforeAll
    public static void beforeAll() {

        List<User> users = api.list();

        Optional<User> user = users.stream().filter(u -> u.getEmail().equals(fixUserEmail)).findFirst();
        if (user.isPresent()) {
            log.debug("Fix user exists already, nothing to do");
            fixUser = user.get();
        } else {
            UserCreationInfo creationInfo = new UserCreationInfo(fixUserFirst, fixUserLast, fixUserEmail);
            fixUser = api.create(creationInfo);
            log.debug("Created Fix user");
        }
    }

    @Test
    void createExistingEmailFails() {
        RequestProcessingException exception = Assertions.assertThrows(RequestProcessingException.class, () -> {
            UserCreationInfo creationInfo = new UserCreationInfo(fixUserFirst, fixUserLast, fixUserEmail);

            api.create(creationInfo);
        });
        assertEquals(422, exception.getHttpCode());
        assertTrue(exception.getMessage().contains(fixUserEmail));
    }

    @Test
    void getSelf() {

        User self = api.getSelf();

        assertNotNull(self.getFirstName());
        assertNotNull(self.getEmail());
    }

    @Test
    void getUser() {
        User test = api.get(fixUser);

        assertEquals(fixUserFirst, test.getFirstName());
    }

    @Test
    void getUserNotExisting() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> api.get(new UserReferenceDto(1)));
        assertEquals(404, e.getHttpCode());
    }

    @Test
    void testChangeEmail() {

        User toChange = new User();
        toChange.setEmail("new@example.org");

        User user = api.update(fixUser, toChange);

        assertEquals(toChange.getEmail(), user.getEmail());

        // restore email
        toChange.setEmail(fixUserEmail);

        api.update(fixUser, toChange);
    }

    @Test
    void testChangeDetails() {

        User toChange = new User();
        toChange.setTimezone("Alaska");
        toChange.setTelephone("0800 800 288");
        toChange.setWeeklyCapacity(40, TimeUnit.HOURS);
        toChange.setDefaultHourlyRate(120.);
        toChange.setCostRate(220.);

        User user = api.update(fixUser, toChange);

        assertEquals(toChange.getTimezone(), user.getTimezone());
        assertEquals(toChange.getTelephone(), user.getTelephone());
        assertEquals(toChange.getWeeklyCapacity(), user.getWeeklyCapacity());
        assertEquals(toChange.getDefaultHourlyRate(), user.getDefaultHourlyRate());
        assertEquals(toChange.getCostRate(), user.getCostRate());
    }

}