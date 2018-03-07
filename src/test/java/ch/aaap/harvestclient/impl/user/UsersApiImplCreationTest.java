package ch.aaap.harvestclient.impl.user;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.ImmutableUser;
import ch.aaap.harvestclient.domain.User;
import util.TestSetupUtil;

@HarvestTest
class UsersApiImplCreationTest {

    private static final UsersApi api = TestSetupUtil.getAdminAccess().users();
    private User randomUserCreationInfo;
    private User temporaryUser;

    @BeforeEach
    void beforeEach() {

        randomUserCreationInfo = TestSetupUtil.getRandomUserCreationInfo();

    }

    @AfterEach
    void afterEach() {

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
    void createProjectManager() {
        User userInfo = ImmutableUser.builder().from(randomUserCreationInfo)
                .projectManager(true)
                .canSeeRates(true)
                .canCreateProjects(true)
                .canCreateInvoices(true)
                .build();

        User user = api.create(userInfo);
        // insure cleanup
        temporaryUser = user;

        assertEquals(userInfo.getProjectManager(), user.getProjectManager());
        assertEquals(userInfo.getCanSeeRates(), user.getCanSeeRates());
        assertEquals(userInfo.getCanCreateProjects(), user.getCanCreateProjects());
        assertEquals(userInfo.getCanCreateInvoices(), user.getCanCreateInvoices());
    }

    @Test
    void createUserFullDetails() {

        User userInfo = ImmutableUser.builder().from(randomUserCreationInfo)
                .timezone("Alaska")
                .telephone("0800 800 288")
                .hasAccessToAllFutureProjects(true)
                .projectManager(false)
                .canSeeRates(false)
                .canCreateProjects(false)
                .canCreateInvoices(false)
                .weeklyCapacity(TimeUnit.HOURS.toSeconds(40))
                .defaultHourlyRate(120.)
                .costRate(220.)
                .roles(Arrays.asList("developer", "manager"))
                .build();

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
