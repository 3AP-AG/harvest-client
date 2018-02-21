package ch.aaap.harvestclient.impl.estimate;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.EstimateUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableEstimateUpdateInfo;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class EstimatesApiUpdateTest {

    private static final EstimatesApi estimatesApi = TestSetupUtil.getAdminAccess().estimates();
    private Estimate estimate;
    private Client client = ExistingData.getInstance().getClient();
    private String kind = ExistingData.getInstance().getEstimateItemCategory().getName();

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        estimate = estimatesApi.create(ImmutableEstimate.builder()
                .client(client)
                .notes("test Estimate for " + testInfo.getDisplayName())
                .build());
    }

    @AfterEach
    void afterEach() {
        if (estimate != null) {
            estimatesApi.delete(estimate);
            estimate = null;
        }
    }

    @Test
    void changeClient() {

        Client anotherClient = ExistingData.getInstance().getAnotherClient();

        EstimateUpdateInfo changes = ImmutableEstimateUpdateInfo.builder()
                .client(anotherClient)
                .build();
        Estimate updatedEstimate = estimatesApi.update(estimate, changes);

        assertThat(updatedEstimate.getClient().getId()).isEqualTo(anotherClient.getId());

    }

    @Test
    void changeAll() {

        EstimateUpdateInfo changes = ImmutableEstimateUpdateInfo.builder()
                // make sure not to take defaults here
                .number("23423")
                .purchaseOrder("does it need to be a number?")
                .tax(1.)
                .tax2(4.)
                .discount(10.)
                .subject("updated subject")
                .notes("updated notes")
                .currency("ZMK")
                .issueDate(LocalDate.now().minusWeeks(3))
                .build();

        Estimate updatedEstimate = estimatesApi.update(estimate, changes);

        assertThat(updatedEstimate).isEqualToComparingOnlyGivenFields(changes, "number", "purchaseOrder", "tax", "tax2",
                "discount", "subject", "notes", "currency", "issueDate");

    }

    @Test
    void changeLineItem() {

        long quantity = 20;
        double unitPrice = 30;
        String firstName = "test First";
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(client)
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

        EstimateUpdateInfo changes = ImmutableEstimateUpdateInfo.builder()
                .addEstimateItem(ImmutableEstimateItem.builder()
                        .quantity(20L)
                        .unitPrice(2.)
                        .kind(kind)
                        .build())
                .build();

        Estimate updatedEstimate = estimatesApi.update(estimate, changes);

        List<EstimateItem> items = updatedEstimate.getEstimateItems();

        // update adds the item to the list, does not remove existing
        assertThat(items).hasSize(3);
    }
}
