package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ClientReferenceDto extends BaseReferenceDto implements Reference<Client> {
    public ClientReferenceDto() {
    }

    public ClientReferenceDto(long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "ClientReferenceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
