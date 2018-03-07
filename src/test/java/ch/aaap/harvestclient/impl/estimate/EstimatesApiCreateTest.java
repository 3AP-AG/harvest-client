package ch.aaap.harvestclient.impl.estimate;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class EstimatesApiCreateTest {

    private static final EstimatesApi estimatesApi = TestSetupUtil.getAdminAccess().estimates();
    private Estimate estimate;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();
    private EstimateItem.Category category = ExistingData.getInstance().getEstimateItemCategory();
    private String kind = category.getName();

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
                .client(clientReference)
                .build();
        estimate = estimatesApi.create(creationInfo);

        assertThat(estimate.getClient().getId()).isEqualTo(clientReference.getId());
        assertThat(estimate.getState() == Estimate.State.DRAFT);
    }

    @Test
    void createAllOptions() {

        // amounts are computed automatically from lineItems
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(clientReference)
                .number("999")
                .purchaseOrder("12332")
                .tax(11.)
                .tax2(34.)
                .discount(50.)
                .subject("test subject")
                .notes("test notes")
                .currency("EUR")
                .issueDate(LocalDate.now().minusWeeks(2))
                // not allowed by Harvest, see Estimate Message API
                // .sentAt(Instant.now())
                // .declinedAt(Instant.now().plusSeconds(100))
                .build();

        estimate = estimatesApi.create(creationInfo);

        assertThat(estimate).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
        assertThat(estimate.getState() == Estimate.State.DRAFT);
    }

    @Test
    void createWithLineItems() {

        long quantity = 20;
        double unitPrice = 30;
        String firstName = "test First";
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(clientReference)
                .addEstimateItem(ImmutableEstimateItem.builder()
                        .quantity(10L)
                        .unitPrice(1.)
                        .kind(kind)
                        .build())
                .addEstimateItem(ImmutableEstimateItem.builder()
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .kind(kind)
                        .build())
                .subject("test subject")
                .build();
        estimate = estimatesApi.create(creationInfo);

        assertThat(estimate.getClient().getId()).isEqualTo(clientReference.getId());
        List<EstimateItem> items = estimate.getEstimateItems();
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getKind()).isEqualTo(kind);
        assertThat(items.get(1).getQuantity()).isEqualTo(quantity);
        assertThat(items.get(1).getAmount()).isEqualTo(quantity * unitPrice);
        assertThat(estimate.getState() == Estimate.State.DRAFT);

    }

}
