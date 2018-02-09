package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ClientContactCreationInfo extends ClientContact {

    private Long clientId;

    public ClientContactCreationInfo(Reference<Client> clientReference, String firstName) {
        clientId = clientReference.getId();
        setFirstName(firstName);
    }

}
