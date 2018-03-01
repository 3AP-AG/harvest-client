package ch.aaap.harvestclient.impl.invoiceMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.aaap.harvestclient.api.InvoiceMessagesApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.exception.RequestProcessingException;
import util.ExistingData;
import util.TestSetupUtil;

public class InvoiceMessagesApiCreateTest {

    private static final MessageRecipient testRecipient = ImmutableMessageRecipient.of("Marco",
            TestSetupUtil.getDevNullEmail());

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();

    private final InvoiceMessagesApi api = harvest.invoiceMessages();

    private Invoice invoice;
    private InvoiceMessage invoiceMessage;

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
        if (invoiceMessage != null) {
            harvest.invoiceMessages().delete(invoice, invoiceMessage);
            invoiceMessage = null;
        }
        if (invoice != null) {
            harvest.invoices().delete(invoice);
            invoice = null;
        }
    }

    @Test
    void createDefault() {

        InvoiceMessage creationInfo = ImmutableInvoiceMessage.builder()
                .addMessageRecipient(testRecipient)
                .subject("test subject")
                // body is required
                .body("test body")
                .build();
        invoiceMessage = api.create(invoice, creationInfo);

        assertThat(invoiceMessage).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createAllOptions() {

        InvoiceMessage creationInfo = ImmutableInvoiceMessage.builder()
                .addMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                // .eventType(InvoiceMessage.EventType.SEND)
                .build();
        invoiceMessage = api.create(invoice, creationInfo);

        assertThat(invoiceMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");
        assertThat(invoiceMessage.getMessageRecipients()).contains(testRecipient);
        assertThat(invoiceMessage.getMessageRecipients()).hasSize(2);
    }

    @EnumSource(value = InvoiceMessage.EventType.class, names = { "RE_OPEN", "VIEW", "CLOSE", "DRAFT" })
    @ParameterizedTest
    void cannot(InvoiceMessage.EventType type) {
        assertThrows(RequestProcessingException.class,
                () -> api.markAs(invoice, type));
    }

    @Test
    void close() {

        api.markAs(invoice, InvoiceMessage.EventType.SEND);

        api.markAs(invoice, InvoiceMessage.EventType.CLOSE);

        // refresh invoice
        invoice = harvest.invoices().get(invoice);
        assertThat(invoice.getClosedAt()).isNotNull();
    }

    @Test
    void send() {

        InvoiceMessage creationInfo = ImmutableInvoiceMessage.builder()
                .addMessageRecipient(testRecipient)
                .subject("test subject")
                .body("This is the body")
                .sendMeACopy(true)
                .eventType(InvoiceMessage.EventType.SEND)
                .build();
        invoiceMessage = api.create(invoice, creationInfo);

        assertThat(invoiceMessage).isEqualToComparingOnlyGivenFields(creationInfo, "subject", "body");

        // refresh invoice
        invoice = harvest.invoices().get(invoice);
        assertThat(invoice.getSentAt()).isNotNull();
    }

    @Test
    void SendDraft() {

        api.markAs(invoice, InvoiceMessage.EventType.SEND);
        refreshInvoice();
        assertThat(invoice.getSentAt()).isNotNull();

        api.markAs(invoice, InvoiceMessage.EventType.DRAFT);

        // TODO how to check for draft? -> wait on Harvest response

    }

}
