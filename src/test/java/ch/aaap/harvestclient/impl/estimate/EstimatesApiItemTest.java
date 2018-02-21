package ch.aaap.harvestclient.impl.estimate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.EstimateItemUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableEstimateItemUpdateInfo;
import util.ExistingData;
import util.TestSetupUtil;

public class EstimatesApiItemTest {

    private static final Logger log = LoggerFactory.getLogger(EstimatesApiItemTest.class);

    private final EstimatesApi api = TestSetupUtil.getAdminAccess().estimates();

    private static final String kind = ExistingData.getInstance().getEstimateItemCategory().getName();

    private static final String anotherKind = ExistingData.getInstance().getAnotherEstimateItemCategory().getName();

    private final Client client = ExistingData.getInstance().getClient();

    private Estimate estimate;

    @BeforeEach
    void beforeEach() {
        estimate = api.create(ImmutableEstimate.builder()
                .client(client)
                .build());
    }

    @AfterEach
    void afterEach() {
        if (estimate != null) {
            api.delete(estimate);
            estimate = null;
        }
    }

    @Test
    void createItem() {

        EstimateItem creationInfo = ImmutableEstimateItem.builder()
                .kind(kind)
                .unitPrice(1.)
                .build();
        estimate = api.addLineItem(estimate, creationInfo);

        assertThat(estimate.getEstimateItems()).isNotEmpty();
        EstimateItem estimateItem = estimate.getEstimateItems().get(0);

        assertThat(estimateItem).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createMultipleItems() {

        List<EstimateItem> creationInfoList = new ArrayList<>();
        creationInfoList.add(ImmutableEstimateItem.builder()
                .kind(kind)
                .unitPrice(2.)
                .build());
        creationInfoList.add(ImmutableEstimateItem.builder()
                .kind(kind)
                .unitPrice(5.)
                .build());
        estimate = api.addLineItems(this.estimate, creationInfoList);

        for (int i = 0; i < creationInfoList.size(); i++) {
            assertThat(estimate.getEstimateItems().get(i)).isEqualToIgnoringNullFields(creationInfoList.get(i));
        }
    }

    @Nested
    class UpdateTest {

        private EstimateItem item;

        @BeforeEach
        void beforeEach() {
            EstimateItem creationInfo = ImmutableEstimateItem.builder()
                    .kind(kind)
                    .unitPrice(1.)
                    .build();
            estimate = api.addLineItem(estimate, creationInfo);
            item = estimate.getEstimateItems().get(0);
        }

        @Disabled("Harvest bug of 21.2")
        @Test
        void updateKind() {

            EstimateItemUpdateInfo updateInfo = ImmutableEstimateItemUpdateInfo.builder()
                    .kind(anotherKind)
                    // this test only works if unitPrice is set
                    // .unitPrice(2.)
                    .build();
            estimate = api.updateLineItem(estimate, item, updateInfo);

            EstimateItem updatedItem = estimate.getEstimateItems().get(0);

            assertThat(updatedItem.getKind()).isEqualTo(anotherKind);

        }

        @Test
        void updateAll() {

            EstimateItemUpdateInfo updateInfo = ImmutableEstimateItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description")
                    .quantity(22L)
                    .unitPrice(33.)
                    .taxed(true)
                    .taxed2(true)
                    .build();
            estimate = api.updateLineItem(estimate, item, updateInfo);

            EstimateItem updatedItem = estimate.getEstimateItems().get(0);

            assertThat(updatedItem).isEqualToIgnoringNullFields(updateInfo);

        }

        @Test
        void updateMultiple() {

            EstimateItem item2 = ImmutableEstimateItem.builder()
                    .kind(kind)
                    .unitPrice(2.)
                    .build();
            estimate = api.addLineItem(estimate, item2);
            item2 = estimate.getEstimateItems().get(1);

            List<EstimateItemUpdateInfo> updateInfoList = new ArrayList<>();
            updateInfoList.add(ImmutableEstimateItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description")
                    .quantity(22L)
                    .unitPrice(33.)
                    .taxed(true)
                    .taxed2(true)
                    .build());
            updateInfoList.add(ImmutableEstimateItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description 2")
                    .quantity(2L)
                    .unitPrice(3.)
                    .taxed(false)
                    .taxed2(true)
                    .build());
            estimate = api.updateLineItems(estimate, Arrays.asList(item, item2), updateInfoList);

            for (int i = 0; i < updateInfoList.size(); i++) {
                EstimateItem updatedItem = estimate.getEstimateItems().get(i);
                assertThat(updatedItem).as("Check item " + i).isEqualToIgnoringNullFields(updateInfoList.get(i));
            }

        }

        @Nested
        class DeleteTest {

            @Test
            void deleteOne() {
                estimate = api.deleteLineItem(EstimatesApiItemTest.this.estimate, item);
                assertThat(estimate.getEstimateItems()).isEmpty();
            }

            @Test
            void deleteMultiple() {

                EstimateItem creationInfo = ImmutableEstimateItem.builder()
                        .kind(kind)
                        .unitPrice(2.)
                        .build();
                estimate = api.addLineItem(estimate, creationInfo);

                estimate = api.deleteLineItems(estimate, estimate.getEstimateItems());

                assertThat(estimate.getEstimateItems()).isEmpty();
            }
        }
    }

}
