package ch.aaap.harvestclient.impl.invoiceCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoiceItemCategoriesApi;
import ch.aaap.harvestclient.domain.ImmutableInvoiceItem;
import ch.aaap.harvestclient.domain.InvoiceItem;
import util.TestSetupUtil;

@HarvestTest
class InvoiceItemCategoriesApiUpdateTest {

    private static final InvoiceItemCategoriesApi api = TestSetupUtil.getAdminAccess().invoiceItemCategories();
    private InvoiceItem.Category category;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        category = api.create(ImmutableInvoiceItem.Category.builder()
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

        InvoiceItem.Category changes = ImmutableInvoiceItem.Category.builder()
                .name("category for test " + testInfo.getDisplayName())
                .build();
        InvoiceItem.Category updatedCategory = api.update(category, changes);

        assertThat(updatedCategory).isEqualToIgnoringNullFields(updatedCategory);

    }

}
