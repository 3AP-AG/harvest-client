package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

public class Estimate {

    private Long id;
    private ClientReferenceDto clientReferenceDto;
    private List<InvoiceLineItem> invoiceLineItemList;
    private Creator creator;
    private String clientKey;

    private String number;
    private String purchaseOrder;

    private Double amount;
    private Double dueAmount;
    private Double tax;
    private Double taxAmount;
    private Double tax2;
    private Double taxAmount2;
    private Double discount;
    private Double discountAmount;

    private String subject;
    private String notes;

    private String currency;

    private LocalDate issueDate;
    private Instant sentAt;

    private Instant acceptedAt;
    private Instant declinedAt;

    private Instant createdAt;
    private Instant updatedAt;
}
