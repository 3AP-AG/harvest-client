package ch.aaap.harvestclient.impl.client.client;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.param.ClientCreationInfo;
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

        ClientCreationInfo creationInfo = new ClientCreationInfo("inactive test Client");
        creationInfo.setActive(false);
        client = clientsApi.create(creationInfo);

        ClientFilter filter = new ClientFilter();
        filter.setActive(false);

        List<Client> clients = clientsApi.list(filter);

        assertThat(clients).usingFieldByFieldElementComparator().containsExactly(client);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        ClientCreationInfo creationInfo = new ClientCreationInfo("newly created test Client");
        client = clientsApi.create(creationInfo);

        ClientFilter filter = new ClientFilter();
        filter.setUpdatedSince(creationTime);

        List<Client> clients = clientsApi.list(filter);

        assertThat(clients).usingFieldByFieldElementComparator().containsExactly(client);

    }

}
