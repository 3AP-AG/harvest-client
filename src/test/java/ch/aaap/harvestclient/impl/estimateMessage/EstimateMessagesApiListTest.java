package ch.aaap.harvestclient.impl.estimateMessage;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.EstimateMessagesApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateMessage;
import ch.aaap.harvestclient.domain.ImmutableEstimate;
import ch.aaap.harvestclient.domain.ImmutableEstimateMessage;
import util.ExistingData;
import util.TestSetupUtil;

public class EstimateMessagesApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static Estimate estimate;

    private final EstimateMessagesApi api = harvest.estimateMessages();

    @BeforeEach
    void beforeEach() {
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(ExistingData.getInstance().getClientReference())
                .build();
        estimate = harvest.estimates().create(creationInfo);
    }

    @AfterEach
    void afterEach() {
        if (estimate != null) {
            harvest.estimates().delete(estimate);
            estimate = null;
        }
    }

    @Test
    void list(TestInfo testInfo) {
        List<EstimateMessage> messages = api.list(estimate, null);

        assertThat(messages).isEmpty();

        EstimateMessage message = api.create(estimate, ImmutableEstimateMessage.builder()
                .subject("message from  " + testInfo.getDisplayName())
                .eventType(EstimateMessage.EventType.SEND)
                .build());

        messages = api.list(estimate, null);

        assertThat(messages).containsExactly(message);
    }

    @Test
    void listByUpdatedSince(TestInfo testInfo) {

        EstimateMessage message = api.create(estimate, ImmutableEstimateMessage.builder()
                .subject("message from  " + testInfo.getDisplayName())
                .eventType(EstimateMessage.EventType.SEND)
                .build());

        List<EstimateMessage> messages = api.list(estimate, Instant.now().plusSeconds(1));

        assertThat(messages).isEmpty();
    }

}
