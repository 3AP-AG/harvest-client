package ch.aaap.harvestclient.impl.estimateCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimateItemCategoriesApi;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.ImmutableEstimateItem;
import util.TestSetupUtil;

@HarvestTest
class EstimatesItemCategoriesApiUpdateTest {

    private static final EstimateItemCategoriesApi api = TestSetupUtil.getAdminAccess().estimateItemCategories();
    private EstimateItem.Category category;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        category = api.create(ImmutableEstimateItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build());
    }

    @AfterEach
    void afterEach() {
        if (category != null) {
            api.delete(category);
            category = null;
        }
    }

    @Test
    void changeName(TestInfo testInfo) {

        EstimateItem.Category changes = ImmutableEstimateItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build();
        EstimateItem.Category updatedCategory = api.update(category, changes);

        assertThat(updatedCategory).isEqualToIgnoringNullFields(updatedCategory);

    }

}
