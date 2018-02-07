package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.ClientReference;
import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

public class ProjectUpdateInfo extends Project {

    /**
     * We cannot serialize the clientReferenceDto directly
     */
    private Long clientId;

    public void setClientReference(ClientReference clientReference) {
        clientId = clientReference.getId();
    }

    @Override
    public void setClientReference(ClientReferenceDto clientReference) {
        clientId = clientReference.getId();
    }
}
