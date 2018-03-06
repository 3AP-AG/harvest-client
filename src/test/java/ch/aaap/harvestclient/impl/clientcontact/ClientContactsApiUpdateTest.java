package ch.aaap.harvestclient.impl.clientcontact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientContactsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.ImmutableClientContact;
import ch.aaap.harvestclient.domain.param.ClientContactUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableClientContactUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ClientContactsApiUpdateTest {

    private static final ClientContactsApi clientContactsApi = TestSetupUtil.getAdminAccess().clientContacts();
    private ClientContact clientContact;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        clientContact = clientContactsApi.create(ImmutableClientContact.builder()
                .client(clientReference)
                .firstName("test ClientContact for " + testInfo.getDisplayName())
                .build());
    }

    @AfterEach
    void afterEach() {
        if (clientContact != null) {
            clientContactsApi.delete(clientContact);
            clientContact = null;
        }
    }

    @Test
    void changeFirstName() {

        ClientContactUpdateInfo changes = ImmutableClientContactUpdateInfo.builder()
                .firstName("new clientContact name")
                .build();
        ClientContact updatedClientContact = clientContactsApi.update(clientContact, changes);

        assertThat(updatedClientContact.getFirstName()).isEqualTo(changes.getFirstName());

    }

    @Test
    void changeAll() {

        // opposites of the defaults
        String title = "Mr. ";
        String firstName = "test first";
        String lastName = "test Last";
        String email = "hey@example.com";
        String phoneOffice = "006 00 12";
        String phoneMobile = "1232 32323 32 ";
        String fax = "this is a fax ?";

        ClientContactUpdateInfo changes = ImmutableClientContactUpdateInfo.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneMobile(phoneMobile)
                .phoneOffice(phoneOffice)
                .fax(fax)
                .build();

        ClientContact updatedClientContact = clientContactsApi.update(clientContact, changes);

        assertThat(updatedClientContact).isEqualToComparingOnlyGivenFields(changes, "title", "firstName", "lastName",
                "email", "phoneOffice", "phoneMobile", "fax");

    }

}
