package ch.aaap.harvestclient.impl.invoice;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class InvoicesApiCreateTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final InvoicesApi invoicesApi = harvest.invoices();
    private Invoice invoice;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();
    private InvoiceItem.Category category = ExistingData.getInstance().getInvoiceItemCategory();
    private String kind = category.getName();

    @AfterEach
    void afterEach() {
        if (invoice != null) {
            invoicesApi.delete(invoice);
            invoice = null;
        }
    }

    @Test
    void create() {

        String firstName = "test First";
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .build();
        invoice = invoicesApi.create(creationInfo);

        assertThat(invoice.getClient().getId()).isEqualTo(clientReference.getId());
        assertThat(invoice.getState(harvest.getSelfTimezone())).isEqualTo(Invoice.State.DRAFT);

    }

    @Test
    void createAllOptions() {

        // amounts are computed automatically from lineItems
        Invoice creationInfo = ImmutableInvoice.builder()
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
                // not allowed by Harvest, see Invoice Message API
                // .sentAt(Instant.now())
                .build();

        invoice = invoicesApi.create(creationInfo);

        assertThat(invoice).usingComparatorForType((x, y) -> (int) (y.getId() - x.getId()), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createWithLineItems() {

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
        invoice = invoicesApi.create(creationInfo);

        assertThat(invoice.getClient().getId()).isEqualTo(clientReference.getId());
        List<InvoiceItem> items = invoice.getInvoiceItems();
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getKind()).isEqualTo(kind);
        assertThat(items.get(1).getQuantity()).isEqualTo(quantity);
        assertThat(items.get(1).getAmount()).isEqualTo(quantity * unitPrice);

    }

    @Test
    void createItemOnlyKindFailsSilent() {

        long quantity = 20;
        double unitPrice = 30;
        String firstName = "test First";
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .kind(kind)
                        .build())
                .subject("test subject")
                .build();
        invoice = invoicesApi.create(creationInfo);

        assertThat(invoice.getClient().getId()).isEqualTo(clientReference.getId());
        List<InvoiceItem> items = invoice.getInvoiceItems();

        // the item is not created, it is silently ignored!
        // TODO it would be nice to prohibit this at compile time
        assertThat(items).isEmpty();

    }

    @Test
    void createWithMinimalLineItem() {

        long quantity = 20;
        double unitPrice = 30;
        String firstName = "test First";
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .unitPrice(1.)
                        .kind(kind)
                        .build())
                .subject("test subject")
                .build();
        invoice = invoicesApi.create(creationInfo);

        assertThat(invoice.getClient().getId()).isEqualTo(clientReference.getId());
        List<InvoiceItem> items = invoice.getInvoiceItems();
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getKind()).isEqualTo(kind);
    }
}
