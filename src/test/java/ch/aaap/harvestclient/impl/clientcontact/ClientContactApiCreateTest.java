package ch.aaap.harvestclient.impl.clientcontact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ClientContactsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.ImmutableClientContact;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ClientContactContactApiCreateTest {

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
    void create() {

        String firstName = "test First";
        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(client)
                .firstName(firstName)
                .build();
        clientContact = clientContactsApi.create(creationInfo);

        assertThat(clientContact.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void createAllOptions() {

        // opposites of the defaults
        String title = "Mr. ";
        String firstName = "test first";
        String lastName = "test Last";
        String email = "hey@example.com";
        String phoneOffice = "006 00 12";
        String phoneMobile = "1232 32323 32 ";
        String fax = "this is a fax ?";

        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(client)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneMobile(phoneMobile)
                .phoneOffice(phoneOffice)
                .fax(fax)
                .build();

        clientContact = clientContactsApi.create(creationInfo);

        assertThat(clientContact).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createInvalidEmail() {

        String firstName = "test first";
        String email = "this is not an email";

        ClientContact creationInfo = ImmutableClientContact.builder()
                .client(client)
                .firstName(firstName)
                .email(email)
                .build();

        assertThatExceptionOfType(RequestProcessingException.class)
                .isThrownBy(() -> clientContact = clientContactsApi.create(creationInfo))
                .withMessageContaining("Email is not valid");
    }

}
