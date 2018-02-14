package ch.aaap.harvestclient.impl.client;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableClient;
import util.TestSetupUtil;

@HarvestTest
class ClientsApiListTest {

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
    void list() {

        List<Client> clients = clientsApi.list(new ClientFilter());

        assertThat(clients).isNotEmpty();

    }

    @Test
    void listByActive() {

        Client creationInfo = ImmutableClient.builder().name("inactive test Client")
                .active(false)
                .build();
        client = clientsApi.create(creationInfo);

        ClientFilter filter = new ClientFilter();
        filter.setActive(false);

        List<Client> clients = clientsApi.list(filter);

        assertThat(clients).usingFieldByFieldElementComparator().containsExactly(client);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        Client creationInfo = ImmutableClient.builder().name("newly created test Client").build();
        client = clientsApi.create(creationInfo);

        ClientFilter filter = new ClientFilter();
        filter.setUpdatedSince(creationTime);

        List<Client> clients = clientsApi.list(filter);

        assertThat(clients).usingFieldByFieldElementComparator().containsExactly(client);

    }

}
