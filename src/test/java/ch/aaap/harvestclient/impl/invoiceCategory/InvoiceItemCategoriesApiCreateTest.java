package ch.aaap.harvestclient.impl.invoiceCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoiceItemCategoriesApi;
import ch.aaap.harvestclient.domain.ImmutableInvoiceItem;
import ch.aaap.harvestclient.domain.InvoiceItem;
import util.TestSetupUtil;

@HarvestTest
class InvoiceItemCategoriesApiCreateTest {

    private static final InvoiceItemCategoriesApi api = TestSetupUtil.getAdminAccess().invoiceItemCategories();
    private InvoiceItem.Category category;

    @AfterEach
    void afterEach() {
        if (category != null) {
            api.delete(category);
            category = null;
        }
    }

    @Test
    void create() {

        InvoiceItem.Category creationInfo = ImmutableInvoiceItem.Category.builder().name("category test name").build();
        category = api.create(creationInfo);

        assertThat(category.getName()).isEqualTo(creationInfo.getName());

    }

}
