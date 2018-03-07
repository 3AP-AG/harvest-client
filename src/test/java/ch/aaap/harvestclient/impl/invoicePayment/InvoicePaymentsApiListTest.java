package ch.aaap.harvestclient.impl.invoicePayment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

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
public class InvoicePaymentsApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static Invoice invoice;

    private final InvoicePaymentsApi api = harvest.invoicePayments();

    @BeforeEach
    void beforeEach() {
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(ExistingData.getInstance().getClientReference())
                .build();
        invoice = harvest.invoices().create(creationInfo);
    }

    @AfterEach
    void afterEach() {
        if (invoice != null) {
            harvest.invoices().delete(invoice);
            invoice = null;
        }
    }

    @Test
    void list(TestInfo testInfo) {
        List<InvoicePayment> payments = api.list(invoice, null);

        assertThat(payments).isEmpty();

        InvoicePayment message = api.create(invoice, ImmutableInvoicePayment.builder()
                .amount(123.)
                .paidDate(LocalDate.now())
                .notes("test notes for " + testInfo.getDisplayName())
                .build());

        payments = api.list(invoice, null);

        assertThat(payments).containsExactly(message);
    }

    @Test
    void listByUpdatedSince(TestInfo testInfo) {

        InvoicePayment message = api.create(invoice, ImmutableInvoicePayment.builder()
                .amount(123.)
                .notes("test notes for " + testInfo.getDisplayName())
                .paidDate(LocalDate.now())
                .build());

        List<InvoicePayment> payments = api.list(invoice, Instant.now().plusSeconds(1));

        assertThat(payments).isEmpty();
    }

}
