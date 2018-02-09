package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

public class Invoice {

    private Long id;
    private ClientReferenceDto clientReferenceDto;
    private List<InvoiceLineItem> invoiceLineItemList;
    private Estimate estimate;
    private Retainer retainer;
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

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Instant sentAt;
    private Instant paidAt;
    private LocalDate paidDate;
    private Instant closedAt;

    private Instant createdAt;
    private Instant updatedAt;

}
