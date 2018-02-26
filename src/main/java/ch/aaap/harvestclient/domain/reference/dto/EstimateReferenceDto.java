package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.reference.Reference;

public class EstimateReferenceDto implements Reference<Estimate> {

    private Long id;

    public EstimateReferenceDto(long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EstimateReferenceDto{" +
                "id=" + id +
                '}';
    }
}
