package ch.aaap.harvestclient.impl.user;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import util.TestSetupUtil;

@HarvestTest
public class UsersApiImplCreationTest {

    private static UsersApi api = TestSetupUtil.getAdminAccess().users();
    private UserCreationInfo randomUserCreationInfo;
    private User temporaryUser;

    @BeforeEach
    public void beforeEach() {

        randomUserCreationInfo = TestSetupUtil.getRandomUserCreationInfo();

    }

    @AfterEach
    public void afterEach() {

        if (temporaryUser != null) {
            api.delete(temporaryUser);
            temporaryUser = null;
        }
    }

    @Test
    void createAndDeleteUser() {

        User user = api.create(randomUserCreationInfo);

        api.delete(user);

    }

    @Test
    // fixed by Harvest on 29.1.18
    public void createProjectManager() {
        UserCreationInfo userInfo = randomUserCreationInfo;
        userInfo.setProjectManager(true);
        userInfo.setCanSeeRates(true);
        userInfo.setCanCreateProjects(true);
        userInfo.setCanCreateInvoices(true);

        User user = api.create(userInfo);
        // insure cleanup
        temporaryUser = user;

        assertEquals(userInfo.getProjectManager(), user.getProjectManager());
        assertEquals(userInfo.getCanSeeRates(), user.getCanSeeRates());
        assertEquals(userInfo.getCanCreateProjects(), user.getCanCreateProjects());
        assertEquals(userInfo.getCanCreateInvoices(), user.getCanCreateInvoices());
    }

    @Test
    public void createUserFullDetails() {

        UserCreationInfo userInfo = randomUserCreationInfo;
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
        temporaryUser = user;

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

}
