package ch.aaap.harvestclient.impl.estimateCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimateItemCategoriesApi;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.ImmutableEstimateItem;
import util.TestSetupUtil;

@HarvestTest
class EstimatesItemCategoriesApiCreateTest {

    private static final EstimateItemCategoriesApi api = TestSetupUtil.getAdminAccess().estimateItemCategories();
    private EstimateItem.Category category;

    @AfterEach
    void afterEach() {
        if (category != null) {
            api.delete(category);
            category = null;
        }
    }

    @Test
    void create() {

        EstimateItem.Category creationInfo = ImmutableEstimateItem.Category.builder()
                .name("category test name")
                .build();
        category = api.create(creationInfo);

        assertThat(category.getName()).isEqualTo(creationInfo.getName());

    }

}
