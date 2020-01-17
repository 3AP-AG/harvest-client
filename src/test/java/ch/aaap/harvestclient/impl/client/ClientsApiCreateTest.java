package ch.aaap.harvestclient.impl.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableClient;
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
        Client creationInfo = ImmutableClient.builder().name(name).build();
        client = clientsApi.create(creationInfo);

        assertThat(client.getName()).isEqualTo(name);

        Client gottenClient = clientsApi.get(client);
        assertThat(gottenClient).isEqualTo(gottenClient);
    }

    @Test
    void createAllOptions() {

        // opposites of the defaults
        String name = "test Client";
        boolean active = false;
        String address = "Road 1,\n 9009 New Amsterdam";
        String currency = "EUR";

        Client creationInfo = ImmutableClient.builder()
                .name(name)
                .active(active)
                .address(address)
                .currency(currency)
                .build();

        client = clientsApi.create(creationInfo);

        assertThat(client).isEqualToIgnoringNullFields(creationInfo);
    }

    /*
     * This test will fail if the test company is setup with a different currency
     */
    @Test
    @Disabled("See https://github.com/3AP-AG/harvest-client/issues/5")
    void createInvalidCurrency() {

        String currency = "EU";

        Client creationInfo = ImmutableClient.builder().name("Test client")
                .currency(currency)
                .build();

        client = clientsApi.create(creationInfo);

        // Company object does not expose currency, this needs to be set to the test
        // account default currency
        // setting an invalid one will set it to the company default
        assertThat(client.getCurrency()).isEqualTo("USD");
    }

}
