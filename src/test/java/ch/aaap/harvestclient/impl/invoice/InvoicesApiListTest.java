package ch.aaap.harvestclient.impl.invoice;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.api.filter.InvoiceFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class InvoicesApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final InvoicesApi invoicesApi = harvest.invoices();
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

    @Test
    void listByProject() {
        Reference<Project> project = ExistingData.getInstance().getProjectReference();

        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .kind(ExistingData.getInstance().getInvoiceItemCategory().getName())
                        .unitPrice(1.)
                        .project(project)
                        .build())
                .build();
        invoice = invoicesApi.create(creationInfo);

        Invoice anotherCreationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .build();
        anotherInvoice = invoicesApi.create(anotherCreationInfo);

        InvoiceFilter filter = new InvoiceFilter();
        filter.setProjectReference(project);

        List<Invoice> invoices = invoicesApi.list(filter);
        assertThat(invoices).contains(invoice);
        assertThat(invoices).doesNotContain(anotherInvoice);

    }

    @Test
    @Disabled("See https://github.com/3AP-AG/harvest-client/issues/5")
    void listByDateRange() {

        Reference<Project> project = ExistingData.getInstance().getProjectReference();

        Invoice creationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .addInvoiceItem(ImmutableInvoiceItem.builder()
                        .kind(ExistingData.getInstance().getInvoiceItemCategory().getName())
                        .unitPrice(1.)
                        .project(project)
                        .build())
                .issueDate(LocalDate.of(2000, 1, 5))
                .build();
        invoice = invoicesApi.create(creationInfo);

        Invoice anotherCreationInfo = ImmutableInvoice.builder()
                .client(clientReference)
                .issueDate(LocalDate.of(2000, 1, 10))
                .build();
        anotherInvoice = invoicesApi.create(anotherCreationInfo);

        InvoiceFilter filter = new InvoiceFilter();
        filter.setFrom(LocalDate.of(2000, 1, 4));
        List<Invoice> invoices = invoicesApi.list(filter);

        assertThat(invoices).contains(invoice);
        assertThat(invoices).contains(anotherInvoice);

        filter.setFrom(LocalDate.of(2000, 1, 6));
        invoices = invoicesApi.list(filter);
        assertThat(invoices).doesNotContain(invoice);
        assertThat(invoices).contains(anotherInvoice);

        filter.setTo(LocalDate.of(2000, 1, 9));
        invoices = invoicesApi.list(filter);
        assertThat(invoices).doesNotContain(invoice);
        assertThat(invoices).doesNotContain(anotherInvoice);

        filter.setTo(LocalDate.of(2000, 1, 11));
        invoices = invoicesApi.list(filter);
        assertThat(invoices).doesNotContain(invoice);
        assertThat(invoices).contains(anotherInvoice);

    }
}
