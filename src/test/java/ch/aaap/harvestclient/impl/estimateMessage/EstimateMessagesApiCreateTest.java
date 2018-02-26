package ch.aaap.harvestclient.impl.estimateMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.aaap.harvestclient.api.EstimateMessagesApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.ExistingData;
import util.TestSetupUtil;

public class EstimateMessagesApiCreateTest {

    private static final EstimateMessageRecipient testRecipient = ImmutableEstimateMessageRecipient.of("Marco",
            TestSetupUtil.getDevNullEmail());

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private final EstimateMessagesApi api = harvest.estimateMessages();

    private Estimate estimate;
    private EstimateMessage estimateMessage;

    private void refreshEstimate() {
        estimate = harvest.estimates().get(estimate);
    }

    @BeforeEach
    void beforeEach() {
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(ExistingData.getInstance().getClientReference())
                .build();
        estimate = harvest.estimates().create(creationInfo);
    }

    @AfterEach
    void afterEach() {
        if (estimateMessage != null) {
            harvest.estimateMessages().delete(estimate, estimateMessage);
            estimateMessage = null;
        }
        if (estimate != null) {
            harvest.estimates().delete(estimate);
            estimate = null;
        }
    }

    @Test
    void createDefault() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addEstimateMessageRecipient(testRecipient)
                .subject("test subject")
                // body is required
                .body("test body")
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createAllOptions() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addEstimateMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                // .eventType(EstimateMessage.EventType.SEND)
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");
        assertThat(estimateMessage.getEstimateMessageRecipients()).contains(testRecipient);
        assertThat(estimateMessage.getEstimateMessageRecipients()).hasSize(2);
    }

    @EnumSource(value = EstimateMessage.EventType.class, names = { "RE_OPEN", "VIEW", "INVOICE" })
    @ParameterizedTest
    void cannot(EstimateMessage.EventType type) {
        assertThrows(RequestProcessingException.class,
                () -> api.markAs(estimate, type));
    }

    @Test
    void accept() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addEstimateMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                .eventType(EstimateMessage.EventType.ACCEPT)
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");

        // refresh estimate
        estimate = harvest.estimates().get(estimate);
        assertThat(estimate.getAcceptedAt()).isNotNull();
    }

    @Test
    void send() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addEstimateMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                .eventType(EstimateMessage.EventType.SEND)
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");

        // refresh estimate
        estimate = harvest.estimates().get(estimate);
        assertThat(estimate.getSentAt()).isNotNull();
    }

    @Test
    void SendAccept() {

        api.markAs(estimate, EstimateMessage.EventType.SEND);
        refreshEstimate();
        assertThat(estimate.getSentAt()).isNotNull();

        api.markAs(estimate, EstimateMessage.EventType.ACCEPT);
        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();

    }

    @Test
    void AcceptSendReAccept() {

        api.markAs(estimate, EstimateMessage.EventType.ACCEPT);
        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();

        // sending will reset the accept status on the website
        // from the API it does not work
        assertThrows(RequestProcessingException.class, () -> api.markAs(estimate, EstimateMessage.EventType.SEND));

    }

    @Test
    void AcceptDecline() {

        EstimateMessage message = api.markAs(estimate, EstimateMessage.EventType.ACCEPT);
        // cannot decline accepted estimate
        assertThrows(RequestProcessingException.class, () -> api.markAs(estimate, EstimateMessage.EventType.DECLINE));

        // deleting a message does not change the status
        api.delete(estimate, message);
        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();

        // still cannot decline
        assertThrows(RequestProcessingException.class, () -> api.markAs(estimate, EstimateMessage.EventType.DECLINE));

    }

}