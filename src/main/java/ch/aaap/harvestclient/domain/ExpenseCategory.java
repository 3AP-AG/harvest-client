package ch.aaap.harvestclient.domain;

import java.time.Instant;

public class ExpenseCategory {

    private Long id;
    private String name;
    private String unitName;
    private Double unitPrice;
    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;
}
