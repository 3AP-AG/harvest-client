package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.reference.ClientReference;

public class ClientReferenceDto extends BaseReferenceDto implements ClientReference {
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
