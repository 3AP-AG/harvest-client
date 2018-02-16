package ch.aaap.harvestclient.impl.clientcontact;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientContactsApi;
import ch.aaap.harvestclient.api.filter.ClientContactFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.ImmutableClientContact;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ClientContactContactsApiListTest {

    private static final ClientContactsApi clientContactsApi = TestSetupUtil.getAdminAccess().clientContacts();
    private ClientContact clientContact;
    private Client client = ExistingData.getInstance().getClient();

    @AfterEach
    void afterEach() {
        if (clientContact != null) {
            clientContactsApi.delete(clientContact);
            clientContact = null;
        }
    }

    @Test
    void list() {

        List<ClientContact> clientContacts = clientContactsApi.list(new ClientContactFilter());

        assertThat(clientContacts).isNotEmpty();

    }

    @Test
    void listByClient() {

        Client anotherClient = ExistingData.getInstance().getAnotherClient();

        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(anotherClient)
                .firstName("inactive test ClientContact")
                .build();
        clientContact = clientContactsApi.create(creationInfo);

        ClientContactFilter filter = new ClientContactFilter();
        filter.setClientReference(anotherClient);

        List<ClientContact> clientContacts = clientContactsApi.list(filter);

        assertThat(clientContacts).usingFieldByFieldElementComparator().contains(clientContact);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(client)
                .firstName("newly created test ClientContact")
                .build();
        clientContact = clientContactsApi.create(creationInfo);

        ClientContactFilter filter = new ClientContactFilter();
        filter.setUpdatedSince(creationTime);

        List<ClientContact> clientContacts = clientContactsApi.list(filter);

        assertThat(clientContacts).usingFieldByFieldElementComparator().containsExactly(clientContact);

    }

}
