package ch.aaap.harvestclient.domain;

public class EstimateLineItem {

    private Long id;
    private String kind; // estimate Item category
    private String description;
    private Long quantity;

    private Double unitPrice;
    private Double amount;

    private Boolean taxed;
    private Boolean taxed2;

}
