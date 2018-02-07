package ch.aaap.harvestclient.domain;


import ch.aaap.harvestclient.domain.reference.ClientReference;

public class Client implements ClientReference {

    private Long id;
    private String name;
    private Boolean active;

    private String address;
    private String currency;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
