package ch.aaap.harvestclient.impl.estimateCategory.category;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimateItemCategoriesApi;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.ImmutableEstimateItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import util.TestSetupUtil;

@HarvestTest
class EstimatesItemCategoriesApiListTest {

    private static final EstimateItemCategoriesApi api = TestSetupUtil.getAdminAccess().estimateItemCategories();
    private EstimateItem.Category anotherCategory;
    private EstimateItem.Category category;

    @AfterEach
    void afterEach() {
        if (category != null) {
            api.delete(category);
            category = null;
        }
        if (anotherCategory != null) {
            api.delete(anotherCategory);
            anotherCategory = null;
        }
    }

    @Test
    void list(TestInfo testInfo) {

        EstimateItem.Category creationInfo = ImmutableEstimateItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build();
        category = api.create(creationInfo);

        List<EstimateItem.Category> categories = api.list(null);

        assertThat(categories).isNotEmpty();

    }

    @Test
    void listPaginated(TestInfo testInfo) {

        ImmutableEstimateItem.Category creationInfo = ImmutableEstimateItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build();
        category = api.create(creationInfo);
        ImmutableEstimateItem.Category anotherCreationInfo = ImmutableEstimateItem.Category.builder()
                .name("Another category for test " + testInfo.getDisplayName())
                .build();
        anotherCategory = api.create(anotherCreationInfo);

        Pagination<EstimateItem.Category> categoryPagination = api.list(null, 1, 1);

        List<EstimateItem.Category> categories = categoryPagination.getList();

        assertThat(categories).hasSize(1);
        assertThat(categoryPagination.getNextPage()).isEqualTo(2);
        assertThat(categoryPagination.getPreviousPage()).isNull();
        assertThat(categoryPagination.getPerPage()).isEqualTo(1);
        assertThat(categoryPagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByUpdatedSince(TestInfo testInfo) {

        Instant creationTime = Instant.now().minusSeconds(5);
        ImmutableEstimateItem.Category creationInfo = ImmutableEstimateItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build();
        category = api.create(creationInfo);

        List<EstimateItem.Category> categories = api.list(creationTime);

        assertThat(categories).usingFieldByFieldElementComparator().containsExactly(category);

    }

}
