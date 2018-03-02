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

    private static final MessageRecipient testRecipient = ImmutableMessageRecipient.of("Marco",
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
                .addMessageRecipient(testRecipient)
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createAllOptions() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                // .eventType(EstimateMessage.EventType.SEND)
                .build();
        estimateMessage = api.create(estimate, creationInfo);

        assertThat(estimateMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");
        assertThat(estimateMessage.getMessageRecipients()).contains(testRecipient);
        assertThat(estimateMessage.getMessageRecipients()).hasSize(2);
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
                .addMessageRecipient(testRecipient)
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
        assertThat(estimate.getState() == Estimate.State.ACCEPTED);
    }

    @Test
    void send() {

        EstimateMessage creationInfo = ImmutableEstimateMessage.builder()
                .addMessageRecipient(testRecipient)
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
        assertThat(estimate.getState() == Estimate.State.OPEN);
    }

    @Test
    void SendAccept() {

        api.markAs(estimate, EstimateMessage.EventType.SEND);
        refreshEstimate();
        assertThat(estimate.getSentAt()).isNotNull();
        assertThat(estimate.getState() == Estimate.State.OPEN);

        api.markAs(estimate, EstimateMessage.EventType.ACCEPT);
        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();
        assertThat(estimate.getState() == Estimate.State.ACCEPTED);

    }

    @Test
    void AcceptSendReAccept() {

        api.markAs(estimate, EstimateMessage.EventType.ACCEPT);
        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();
        assertThat(estimate.getState() == Estimate.State.ACCEPTED);

        // sending directly will not work, we need to reopen the estimate
        assertThrows(RequestProcessingException.class, () -> api.markAs(estimate, EstimateMessage.EventType.SEND));

        api.markAs(estimate, EstimateMessage.EventType.RE_OPEN);
        assertThat(estimate.getState() == Estimate.State.OPEN);

        api.markAs(estimate, EstimateMessage.EventType.SEND);
        refreshEstimate();
        assertThat(estimate.getSentAt()).isNotNull();
        assertThat(estimate.getAcceptedAt()).isNull();

        api.markAs(estimate, EstimateMessage.EventType.ACCEPT);

        refreshEstimate();
        assertThat(estimate.getAcceptedAt()).isNotNull();
        assertThat(estimate.getState() == Estimate.State.ACCEPTED);

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
        assertThat(estimate.getState() == Estimate.State.ACCEPTED);

        // still cannot decline
        assertThrows(RequestProcessingException.class, () -> api.markAs(estimate, EstimateMessage.EventType.DECLINE));

    }

}
