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
import ch.aaap.harvestclient.domain.param.ClientContactCreationInfo;
import ch.aaap.harvestclient.domain.param.ClientContactUpdateInfo;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ClientContactContactApiUpdateTest {

    private static final ClientContactsApi clientContactsApi = TestSetupUtil.getAdminAccess().clientContacts();
    private ClientContact clientContact;
    private Client client = ExistingData.getInstance().getClient();

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        clientContact = clientContactsApi
                .create(new ClientContactCreationInfo(client, "test ClientContact for " + testInfo.getDisplayName()));
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

        ClientContactUpdateInfo changes = new ClientContactUpdateInfo();
        changes.setFirstName("new clientContact name");
        ClientContact updatedClientContact = clientContactsApi.update(clientContact, changes);

        assertThat(updatedClientContact).isEqualToIgnoringNullFields(changes);

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

        ClientContactUpdateInfo changes = new ClientContactUpdateInfo();
        changes.setFirstName(firstName);
        changes.setLastName(lastName);
        changes.setEmail(email);
        changes.setPhoneOffice(phoneOffice);
        changes.setPhoneMobile(phoneMobile);
        changes.setFax(fax);

        ClientContact updatedClientContact = clientContactsApi.update(clientContact, changes);

        assertThat(updatedClientContact).isEqualToIgnoringNullFields(changes);

    }

}
