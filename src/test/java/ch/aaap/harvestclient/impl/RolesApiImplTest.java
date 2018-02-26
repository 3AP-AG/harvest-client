package ch.aaap.harvestclient.impl;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.RolesApi;
import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Role;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.RoleInfo;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.TestSetupUtil;

@HarvestTest
class RolesApiImplTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private static final RolesApi rolesApi = harvest.roles();
    private static final UsersApi usersApi = harvest.users();

    private final static String TEST_ROLE_NAME = "test Role";
    private static Role testRole;

    @BeforeAll
    static void beforeAll() {

        RoleInfo roleInfo = new RoleInfo(TEST_ROLE_NAME);

        testRole = rolesApi.create(roleInfo);
    }

    @AfterAll
    static void afterAll() {

        if (testRole != null) {
            rolesApi.delete(testRole);
            testRole = null;
        }

    }

    @Test
    void listAndUpdate() {

        List<Role> roles = rolesApi.list();

        assertThat(roles).isNotEmpty();
        assertThat(roles).extracting("name").contains(TEST_ROLE_NAME);

        Role gottenRole = rolesApi.get(testRole);

        assertThat(gottenRole).isEqualToComparingFieldByField(testRole);

        RoleInfo roleInfo = new RoleInfo("new test name");

        Role updatedRole = rolesApi.update(gottenRole, roleInfo);

        assertThat(updatedRole.getName()).isEqualTo("new test name");

    }

    @Test
    void testCreateExistingFails() {
        assertThrows(RequestProcessingException.class, () -> rolesApi.create(new RoleInfo(testRole.getName())));
    }

    @Test
    void testAddAndRemoveUserToRole(TestInfo testInfo) {

        // create a random user
        User user = usersApi.create(TestSetupUtil.getRandomUserCreationInfo());
        try {

            assertThat(user.getRoles()).isEmpty();
            assertThat(testRole.getUserIds()).doesNotContain(user.getId());

            // assign role
            testRole = rolesApi.addUser(testRole, user);
            user = usersApi.get(user);

            assertThat(testRole.getUserIds()).contains(user.getId());
            assertThat(user.getRoles()).contains(testRole.getName());

            // remove role
            testRole = rolesApi.removeUser(testRole, user);
            user = usersApi.get(user);

            assertThat(testRole.getUserIds()).doesNotContain(user.getId());
            assertThat(user.getRoles()).doesNotContain(testRole.getName());
        }

        finally {
            usersApi.delete(user);
        }

    }

    @Test
    void testCreateAndDelete() {

        // role does not exist
        String tempRoleName = "temp role";
        List<Role> roles = rolesApi.list();
        assertThat(roles).extracting("name").doesNotContain(tempRoleName);

        // create role
        RoleInfo roleInfo = new RoleInfo(tempRoleName);
        Role role = rolesApi.create(roleInfo);

        // list finds it
        roles = rolesApi.list();
        assertThat(roles).extracting("name").contains(role.getName());
        assertThat(roles).extracting("id").contains(role.getId());

        rolesApi.delete(role);

        // role is gone
        roles = rolesApi.list();
        assertThat(roles).extracting("name").doesNotContain(role.getName());
        assertThat(roles).extracting("id").doesNotContain(role.getId());

    }

}