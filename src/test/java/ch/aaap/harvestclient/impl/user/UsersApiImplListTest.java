package ch.aaap.harvestclient.impl.user;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.domain.ImmutableUser;
import ch.aaap.harvestclient.domain.User;
import util.TestSetupUtil;

@HarvestTest
class UsersApiImplListTest {

    private static final UsersApi api = TestSetupUtil.getAdminAccess().users();

    private static User activeUser;

    private static User inactiveUser;

    private static Instant userCreationTime;

    @BeforeAll
    static void beforeAll() {

        activeUser = api.create(TestSetupUtil.getRandomUserCreationInfo());
        // Harvest API will round to seconds, calling Instant.now() is not too close
        userCreationTime = activeUser.getCreatedAt().minusSeconds(1);
        User randomUserCreationInfo = ImmutableUser.builder()
                .from(TestSetupUtil.getRandomUserCreationInfo())
                .active(false).build();

        inactiveUser = api.create(randomUserCreationInfo);

    }

    @AfterAll
    static void afterAll() {
        if (activeUser != null) {
            api.delete(activeUser);
        }
        if (inactiveUser != null) {
            api.delete(inactiveUser);
        }
    }

    @Test
    void list() {

        List<User> users = api.list();

        assertThat(users).extracting("id").contains(activeUser.getId(), inactiveUser.getId());
    }

    @Test
    void listFilterByActive() {

        List<User> allUsers = api.list();

        List<User> inactiveUsers = api.list(false, null);

        assertThat(inactiveUsers.get(0)).isEqualToComparingFieldByField(inactiveUser);

        List<User> activeUsers = api.list(true, null);

        assertThat(activeUsers).extracting("id").contains(activeUser.getId());
        assertThat(activeUsers).extracting("id").doesNotContain(inactiveUser.getId());

    }

    @Test
    void listFilterByUpdatedSince() {

        List<User> allUsers = api.list();
        List<User> newUsers = api.list(null, userCreationTime);

        // we could see some users that were created in other tests
        assertThat(newUsers.size()).isGreaterThanOrEqualTo(2);
        assertThat(newUsers).extracting("id").contains(activeUser.getId(), inactiveUser.getId());

    }

}
