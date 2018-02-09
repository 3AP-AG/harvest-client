package ch.aaap.harvestclient.impl.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.param.ClientCreationInfo;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import util.TestSetupUtil;

@HarvestTest
class ClientsApiUpdateTest {

    private static final ClientsApi clientsApi = TestSetupUtil.getAdminAccess().clients();
    private Client client;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        client = clientsApi.create(new ClientCreationInfo("test Client for " + testInfo.getDisplayName()));
    }

    @AfterEach
    void afterEach() {
        if (client != null) {
            clientsApi.delete(client);
            client = null;
        }
    }

    @Test
    void changeName() {

        ClientUpdateInfo changes = new ClientUpdateInfo();
        changes.setName("new client name");
        Client updatedClient = clientsApi.update(client, changes);

        assertThat(updatedClient).isEqualToIgnoringNullFields(changes);

    }

    @Test
    void changeAll() {

        // opposites of the defaults
        String name = "updated test Client";
        boolean active = false;
        String address = "Road 1,\n 8888 New Amsterdam";
        String currency = "EUR";

        ClientUpdateInfo changes = new ClientUpdateInfo();
        changes.setName(name);
        changes.setActive(active);
        changes.setAddress(address);
        changes.setCurrency(currency);
        Client updatedClient = clientsApi.update(client, changes);

        assertThat(updatedClient).isEqualToIgnoringNullFields(changes);

    }

}
