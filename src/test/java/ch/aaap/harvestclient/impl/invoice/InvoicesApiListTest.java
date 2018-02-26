package ch.aaap.harvestclient.impl.invoice;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.api.filter.InvoiceFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableInvoice;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class InvoicesApiListTest {

    private static final InvoicesApi invoicesApi = TestSetupUtil.getAdminAccess().invoices();
    private Invoice anotherInvoice;
    private Invoice invoice;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();

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
    void list() {

        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .build();
        invoice = invoicesApi.create(creationInfo);

        List<Invoice> invoices = invoicesApi.list(new InvoiceFilter());

        assertThat(invoices).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .build();
        anotherInvoice = invoicesApi.create(creationInfo);
        invoice = invoicesApi.create(creationInfo);

        Pagination<Invoice> invoices = invoicesApi.list(new InvoiceFilter(), 1, 1);

        List<Invoice> contacts = invoices.getList();

        assertThat(contacts).hasSize(1);
        assertThat(invoices.getNextPage()).isEqualTo(2);
        assertThat(invoices.getPreviousPage()).isNull();
        assertThat(invoices.getPerPage()).isEqualTo(1);
        assertThat(invoices.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByClient() {

        Reference<Client> anotherClientReference = ExistingData.getInstance().getAnotherClientReference();

        Invoice creationInfo = ImmutableInvoice.builder()
                .client(anotherClientReference)
                .build();
        invoice = invoicesApi.create(creationInfo);

        InvoiceFilter filter = new InvoiceFilter();
        filter.setClientReference(anotherClientReference);

        List<Invoice> invoices = invoicesApi.list(filter);

        assertThat(invoices).usingFieldByFieldElementComparator().contains(invoice);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .build();
        invoice = invoicesApi.create(creationInfo);

        InvoiceFilter filter = new InvoiceFilter();
        filter.setUpdatedSince(creationTime);

        List<Invoice> invoices = invoicesApi.list(filter);

        assertThat(invoices).usingFieldByFieldElementComparator().containsExactly(invoice);

    }

}
