package ch.aaap.harvestclient.impl.invoiceCategory;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoiceItemCategoriesApi;
import ch.aaap.harvestclient.domain.ImmutableInvoiceItem;
import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import util.TestSetupUtil;

@HarvestTest
class InvoiceItemCategoriesApiListTest {

    private static final InvoiceItemCategoriesApi api = TestSetupUtil.getAdminAccess().invoiceItemCategories();
    private InvoiceItem.Category anotherCategory22;
    private InvoiceItem.Category category;

    @AfterEach
    void afterEach() {
        if (category != null) {
            api.delete(category);
            category = null;
        }
        if (anotherCategory22 != null) {
            api.delete(anotherCategory22);
            anotherCategory22 = null;
        }
    }

    @Test
    void list(TestInfo testInfo) {

        InvoiceItem.Category creationInfo = ImmutableInvoiceItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName()).build();
        category = api.create(creationInfo);

        List<InvoiceItem.Category> categories = api.list(null);

        assertThat(categories).isNotEmpty();

    }

    @Test
    void listPaginated(TestInfo testInfo) {

        ImmutableInvoiceItem.Category creationInfo = ImmutableInvoiceItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName()).build();
        category = api.create(creationInfo);
        ImmutableInvoiceItem.Category anotherCreationInfo = ImmutableInvoiceItem.Category.builder()
                .name("Another category for test " + testInfo.getDisplayName()).build();
        anotherCategory22 = api.create(anotherCreationInfo);

        Pagination<InvoiceItem.Category> categoryPagination = api.list(null, 1, 1);

        List<InvoiceItem.Category> categories = categoryPagination.getList();

        assertThat(categories).hasSize(1);
        assertThat(categoryPagination.getNextPage()).isEqualTo(2);
        assertThat(categoryPagination.getPreviousPage()).isNull();
        assertThat(categoryPagination.getPerPage()).isEqualTo(1);
        assertThat(categoryPagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByUpdatedSince(TestInfo testInfo) {

        Instant creationTime = Instant.now().minusSeconds(5);
        ImmutableInvoiceItem.Category creationInfo = ImmutableInvoiceItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName()).build();
        category = api.create(creationInfo);

        List<InvoiceItem.Category> categories = api.list(creationTime);

        assertThat(categories).usingFieldByFieldElementComparator().containsExactly(category);

    }

}
