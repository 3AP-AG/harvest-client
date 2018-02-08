package ch.aaap.harvestclient.domain;

import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;

public class InvoiceLineItem {

    private Long id;
    private ProjectReferenceDto projectReferenceDto;

    private String kind; // Invoice Item Category

    private String description;
    private Long quantity;
    private Double unitPrice;
    private Double amount;

    private Boolean taxed;
    private Boolean taxed2;
}
