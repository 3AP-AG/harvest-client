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
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ClientContactsApiListTest {

    private static final ClientContactsApi clientContactsApi = TestSetupUtil.getAdminAccess().clientContacts();
    private ClientContact clientContact;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();

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
    void listPaginated() {

        Pagination<ClientContact> clientContacts = clientContactsApi.list(new ClientContactFilter(), 1, 1);

        List<ClientContact> contacts = clientContacts.getList();

        assertThat(contacts).hasSize(1);
        assertThat(clientContacts.getNextPage()).isEqualTo(2);
        assertThat(clientContacts.getPreviousPage()).isNull();
        assertThat(clientContacts.getPerPage()).isEqualTo(1);
        assertThat(clientContacts.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByClient() {

        Reference<Client> anotherClientReference = ExistingData.getInstance().getAnotherClientReference();

        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(anotherClientReference)
                .firstName("inactive test ClientContact")
                .build();
        clientContact = clientContactsApi.create(creationInfo);

        ClientContactFilter filter = new ClientContactFilter();
        filter.setClientReference(anotherClientReference);

        List<ClientContact> clientContacts = clientContactsApi.list(filter);

        assertThat(clientContacts).usingFieldByFieldElementComparator().contains(clientContact);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(clientReference)
                .firstName("newly created test ClientContact")
                .build();
        clientContact = clientContactsApi.create(creationInfo);

        ClientContactFilter filter = new ClientContactFilter();
        filter.setUpdatedSince(creationTime);

        List<ClientContact> clientContacts = clientContactsApi.list(filter);

        assertThat(clientContacts).usingFieldByFieldElementComparator().containsExactly(clientContact);

    }

}
