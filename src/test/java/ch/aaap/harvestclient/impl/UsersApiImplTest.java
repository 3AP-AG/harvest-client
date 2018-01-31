package ch.aaap.harvestclient.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.UserReferenceDto;
import ch.aaap.harvestclient.exception.NotFoundException;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.TestSetupUtil;

class UsersApiImplTest {

    private final static Logger log = LoggerFactory.getLogger(UsersApiImplTest.class);
    // user that is assumed to exist already
    private final static String fixUserFirst = "FixFirst";
    private final static String fixUserLast = "FixLast";
    private final static String fixUserEmail = "fix.user@example.com";
    // user created and deleted in every test
    private static String userFirst = "First";
    private static String userLast = "Last";
    private static String userEmail = "test@example.com";
    private static UsersApi api = TestSetupUtil.getAdminAccess().users();
    private static User fixUser;

    private final Random randomGenerator = new Random();

    private User testUser;

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

    @BeforeEach
    public void beforeEach() {

        userEmail = "test" + randomGenerator.nextInt(1_000_000) + "@example.com";

    }

    @AfterEach
    public void afterEach() {

        if (testUser != null) {
            api.delete(testUser);
            testUser = null;
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
            UserCreationInfo creationInfo = new UserCreationInfo(fixUserFirst, fixUserLast, fixUserEmail);

            api.create(creationInfo);
        });
        assertEquals(422, exception.getHttpCode());
        assertTrue(exception.getMessage().contains(fixUserEmail));
    }

    @Test
    void createAndDeleteUser() {

        UserCreationInfo creationInfo = new UserCreationInfo(userFirst, userLast, userEmail);

        User user = api.create(creationInfo);

        api.delete(user);

    }

    @Test
    // fixed by Harvest on 29.1.18
    public void createProjectManager() {
        UserCreationInfo userInfo = new UserCreationInfo(userFirst, userLast, userEmail);
        userInfo.setProjectManager(true);
        userInfo.setCanSeeRates(true);
        userInfo.setCanCreateProjects(true);
        userInfo.setCanCreateInvoices(true);

        User user = api.create(userInfo);
        // insure cleanup
        testUser = user;

        assertEquals(userInfo.getProjectManager(), user.getProjectManager());
        assertEquals(userInfo.getCanSeeRates(), user.getCanSeeRates());
        assertEquals(userInfo.getCanCreateProjects(), user.getCanCreateProjects());
        assertEquals(userInfo.getCanCreateInvoices(), user.getCanCreateInvoices());
    }

    @Test
    public void createUserFullDetails() {

        UserCreationInfo userInfo = new UserCreationInfo(userFirst, userLast, userEmail);
        userInfo.setTimezone("Alaska");
        userInfo.setTelephone("0800 800 288");
        userInfo.setHasAccessToAllFutureProjects(true);
        userInfo.setProjectManager(false);
        userInfo.setCanSeeRates(false);
        userInfo.setCanCreateProjects(false);
        userInfo.setCanCreateInvoices(false);
        userInfo.setWeeklyCapacity(40, TimeUnit.HOURS);
        userInfo.setDefaultHourlyRate(120.);
        userInfo.setCostRate(220.);
        userInfo.setRoles(Arrays.asList("developer", "manager"));

        User user = api.create(userInfo);
        // insure cleanup
        testUser = user;

        assertEquals(userInfo.getTimezone(), user.getTimezone());
        assertEquals(userInfo.getTelephone(), user.getTelephone());
        assertEquals(userInfo.getHasAccessToAllFutureProjects(), user.getHasAccessToAllFutureProjects());
        assertEquals(userInfo.getProjectManager(), user.getProjectManager());
        assertEquals(userInfo.getCanSeeRates(), user.getCanSeeRates());
        assertEquals(userInfo.getCanCreateInvoices(), user.getCanCreateInvoices());
        assertEquals(userInfo.getCanCreateProjects(), user.getCanCreateProjects());
        assertEquals(userInfo.getWeeklyCapacity(), user.getWeeklyCapacity());

        assertEquals(userInfo.getDefaultHourlyRate(), user.getDefaultHourlyRate());
        assertEquals(userInfo.getCostRate(), user.getCostRate());
        assertEquals(userInfo.getRoles(), user.getRoles());
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