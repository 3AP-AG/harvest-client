package ch.aaap.harvestclient.impl.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.param.ClientCreationInfo;
import util.TestSetupUtil;

@HarvestTest
class ClientsApiCreateTest {

    private static final ClientsApi clientsApi = TestSetupUtil.getAdminAccess().clients();
    private Client client;

    @AfterEach
    void afterEach() {
        if (client != null) {
            clientsApi.delete(client);
            client = null;
        }
    }

    @Test
    void create() {

        String name = "test Client";
        ClientCreationInfo creationInfo = new ClientCreationInfo(name);
        client = clientsApi.create(creationInfo);

        assertThat(client.getName()).isEqualTo(name);
    }

    @Test
    void createAllOptions() {

        // opposites of the defaults
        String name = "test Client";
        boolean active = false;
        String address = "Road 1,\n 9009 New Amsterdam";
        String currency = "EUR";

        ClientCreationInfo creationInfo = new ClientCreationInfo(name);
        creationInfo.setActive(active);
        creationInfo.setAddress(address);
        creationInfo.setCurrency(currency);

        client = clientsApi.create(creationInfo);

        assertThat(client).isEqualToIgnoringNullFields(creationInfo);
    }

    /*
     * This test will fail if the test company is setup with a different currency
     */
    @Test
    void createInvalidCurrency() {

        String currency = "EU";

        ClientCreationInfo creationInfo = new ClientCreationInfo("Test client");
        creationInfo.setCurrency(currency);

        client = clientsApi.create(creationInfo);

        // Company object does not expose currency
        // setting an invalid one will set it to the company default
        assertThat(client.getCurrency()).isEqualTo("CHF");
    }

}
