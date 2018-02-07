package ch.aaap.harvestclient.domain;

import ch.aaap.harvestclient.domain.reference.ClientReference;

public class Client implements ClientReference {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
