package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class InvoiceMessage {

    private Long id;
    private String sentBy;
    private String sentByEmail;
    private String sentFrom;
    private String sentFromEmail;
    private List<InvoiceMessageRecipient> invoiceMessageRecipients;

    private String subject;
    private String body;
    private Boolean includeLinkToClientInvoice;
    private Boolean attachPdf;
    private Boolean sendMeACopy;
    private Boolean thankYou;
    private Boolean eventType;

    private Boolean reminder;
    private LocalDate sendReminderOn;

    private Instant createdAt;
    private Instant updatedAt;
}
