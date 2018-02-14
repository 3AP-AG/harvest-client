package ch.aaap.harvestclient.impl.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableClient;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableClientUpdateInfo;
import util.TestSetupUtil;

@HarvestTest
class ClientsApiUpdateTest {

    private static final ClientsApi clientsApi = TestSetupUtil.getAdminAccess().clients();
    private Client client;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        client = clientsApi
                .create(ImmutableClient.builder().name("test Client for " + testInfo.getDisplayName()).build());
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

        ClientUpdateInfo changes = ImmutableClientUpdateInfo.builder().name("new client name").build();
        Client updatedClient = clientsApi.update(client, changes);

        assertThat(updatedClient).isEqualToComparingOnlyGivenFields(changes, "name");

    }

    @Test
    void changeAll() {

        // opposites of the defaults
        String name = "updated test Client";
        boolean active = false;
        String address = "Road 1,\n 8888 New Amsterdam";
        String currency = "EUR";

        ClientUpdateInfo changes = ImmutableClientUpdateInfo.builder()
                .name("new client name")
                .active(active)
                .address(address)
                .currency(currency)
                .build();
        Client updatedClient = clientsApi.update(client, changes);

        assertThat(updatedClient).isEqualToComparingOnlyGivenFields(changes, "name", "active", "address", "currency");

    }

}
