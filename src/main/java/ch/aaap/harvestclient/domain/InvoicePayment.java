package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;

public class InvoicePayment {

    private Long id;
    private String amount;
    private Instant paidAt;
    private LocalDate paidDate;

    private String recordedBy;
    private String recordedByEmail;

    private String notes;
    private String transactionId;
    private PaymentGateway paymentGateway;

    private Instant createdAt;
    private Instant updatedAt;
}
