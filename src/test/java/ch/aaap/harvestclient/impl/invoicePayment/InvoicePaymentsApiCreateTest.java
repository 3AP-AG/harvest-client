package ch.aaap.harvestclient.impl.invoicePayment;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoicePaymentsApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableInvoice;
import ch.aaap.harvestclient.domain.ImmutableInvoicePayment;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoicePayment;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
public class InvoicePaymentsApiCreateTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private final InvoicePaymentsApi api = harvest.invoicePayments();

    private Invoice invoice;
    private InvoicePayment invoicePayment;

    private void refreshInvoice() {
        invoice = harvest.invoices().get(invoice);
    }

    @BeforeEach
    void beforeEach() {
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(ExistingData.getInstance().getClientReference())
                .build();
        invoice = harvest.invoices().create(creationInfo);
    }

    @AfterEach
    void afterEach() {
        if (invoicePayment != null) {
            harvest.invoicePayments().delete(invoice, invoicePayment);
            invoicePayment = null;
        }
        if (invoice != null) {
            harvest.invoices().delete(invoice);
            invoice = null;
        }
    }

    @Test
    void createDefault() {

        InvoicePayment creationInfo = ImmutableInvoicePayment.builder()
                .amount(123.)
                .paidDate(LocalDate.now())
                .build();
        invoicePayment = api.create(invoice, creationInfo);

        assertThat(invoicePayment).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createAllOptions() {

        InvoicePayment creationInfo = ImmutableInvoicePayment.builder()
                .amount(23.4)
                .notes("Test notes")
                .paidAt(Instant.now())
                .build();
        invoicePayment = api.create(invoice, creationInfo);

        assertThat(invoicePayment)
                // harvest does not store nanoseconds
                .usingComparatorForType(Comparator.comparing(
                        t -> t.truncatedTo(ChronoUnit.SECONDS)), Instant.class)
                .isEqualToIgnoringNullFields(creationInfo);

    }

}
