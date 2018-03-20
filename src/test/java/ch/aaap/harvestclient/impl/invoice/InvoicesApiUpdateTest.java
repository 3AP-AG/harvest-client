package ch.aaap.harvestclient.impl.invoice;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.ImmutableInvoiceUpdateInfo;
import ch.aaap.harvestclient.domain.param.InvoiceUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class InvoicesApiUpdateTest {

    private static final InvoicesApi invoicesApi = TestSetupUtil.getAdminAccess().invoices();
    private Invoice invoice;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();
    private Invoice anotherInvoice;
    private String kind = ExistingData.getInstance().getInvoiceItemCategory().getName();

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        invoice = invoicesApi.create(ImmutableInvoice.builder()
                .client(clientReference)
                .notes("test Invoice for " + testInfo.getDisplayName())
                .build());
    }

    @AfterEach
    void afterEach() {
        if (invoice != null) {
            invoicesApi.delete(invoice);
            invoice = null;
        }
        if (anotherInvoice != null) {
            invoicesApi.delete(anotherInvoice);
            anotherInvoice = null;
        }
    }

    @Test
    void changeClient() {

        Reference<Client> anotherClientReference = ExistingData.getInstance().getAnotherClientReference();

        InvoiceUpdateInfo changes = ImmutableInvoiceUpdateInfo.builder()
                .client(anotherClientReference)
                .build();
        Invoice updatedInvoice = invoicesApi.update(invoice, changes);

        assertThat(updatedInvoice.getClient().getId()).isEqualTo(anotherClientReference.getId());

    }

    @Test
    void changeAll() {

        InvoiceUpdateInfo changes = ImmutableInvoiceUpdateInfo.builder()
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

        Invoice updatedInvoice = invoicesApi.update(invoice, changes);

        assertThat(updatedInvoice).isEqualToComparingOnlyGivenFields(changes, "number", "purchaseOrder", "tax", "tax2",
                "discount", "subject", "notes", "currency", "issueDate");

    }

    @Test
    void changeLineItem() {

        double quantity = 20;
        double unitPrice = 30;
        String firstName = "test First";
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .quantity(10.)
                        .unitPrice(1.)
                        .kind(kind)
                        .build())
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .kind(kind)
                        .build())
                .subject("test subject")
                .build();
        anotherInvoice = invoicesApi.create(creationInfo);

        InvoiceUpdateInfo changes = ImmutableInvoiceUpdateInfo.builder()
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .quantity(20.)
                        .unitPrice(2.)
                        .kind(kind)
                        .build())
                .build();

        Invoice updatedInvoice = invoicesApi.update(anotherInvoice, changes);

        List<InvoiceItem> items = updatedInvoice.getInvoiceItems();

        // update adds the item to the list, does not remove existing
        assertThat(items).hasSize(3);
    }
}
