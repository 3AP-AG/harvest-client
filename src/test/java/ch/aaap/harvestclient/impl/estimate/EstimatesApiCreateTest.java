package ch.aaap.harvestclient.impl.estimate;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.ImmutableEstimate;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class EstimatesApiCreateTest {

    private static final EstimatesApi estimatesApi = TestSetupUtil.getAdminAccess().estimates();
    private Estimate estimate;
    private Client client = ExistingData.getInstance().getClient();

    @AfterEach
    void afterEach() {
        if (estimate != null) {
            estimatesApi.delete(estimate);
            estimate = null;
        }
    }

    @Test
    void create() {

        String firstName = "test First";
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(client)
                .build();
        estimate = estimatesApi.create(creationInfo);

        assertThat(estimate.getClient().getId()).isEqualTo(client.getId());

    }

    @Test
    void createAllOptions() {

        // amounts are computed automatically from lineItems
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(client)
                .number("999")
                .purchaseOrder("12332")
                .tax(11.)
                .tax2(34.)
                .discount(50.)
                .subject("test subject")
                .notes("test notes")
                .currency("EUR")
                .issueDate(LocalDate.now().minusWeeks(2))
                // not allowed by Harvest
                // .sentAt(Instant.now())
                // .declinedAt(Instant.now().plusSeconds(100))
                .build();

        estimate = estimatesApi.create(creationInfo);

        assertThat(estimate).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }

}
