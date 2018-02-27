package ch.aaap.harvestclient.impl.invoiceMessage;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.InvoiceMessagesApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableInvoice;
import ch.aaap.harvestclient.domain.ImmutableInvoiceMessage;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoiceMessage;
import util.ExistingData;
import util.TestSetupUtil;

public class InvoiceMessagesApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static Invoice invoice;

    private final InvoiceMessagesApi api = harvest.invoiceMessages();

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
        List<InvoiceMessage> messages = api.list(invoice, null);

        assertThat(messages).isEmpty();

        InvoiceMessage message = api.create(invoice, ImmutableInvoiceMessage.builder()
                .subject("message from  " + testInfo.getDisplayName())
                .eventType(InvoiceMessage.EventType.SEND)
                .build());

        messages = api.list(invoice, null);

        assertThat(messages).containsExactly(message);
    }

    @Test
    void listByUpdatedSince(TestInfo testInfo) {

        InvoiceMessage message = api.create(invoice, ImmutableInvoiceMessage.builder()
                .subject("message from  " + testInfo.getDisplayName())
                .eventType(InvoiceMessage.EventType.SEND)
                .build());

        List<InvoiceMessage> messages = api.list(invoice, Instant.now().plusSeconds(1));

        assertThat(messages).isEmpty();
    }

}
