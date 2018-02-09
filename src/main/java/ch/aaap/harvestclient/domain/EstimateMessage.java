package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.util.List;

public class EstimateMessage {
    private Long id;
    private String sentBy;
    private String sentByEmail;
    private String sentFrom;
    private String sentFromEmail;
    private List<EstimateMessageRecipient> invoiceMessageRecipients;

    private String subject;
    private String body;
    private Boolean sendMeACopy;
    private Boolean eventType;

    private Instant createdAt;
    private Instant updatedAt;

}
