package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.InvoicePaymentsApi;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoicePayment;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.InvoicePaymentService;
import retrofit2.Call;

public class InvoicePaymentsApiImpl implements InvoicePaymentsApi {

    private static final Logger log = LoggerFactory.getLogger(InvoicePaymentsApiImpl.class);
    private InvoicePaymentService service;

    public InvoicePaymentsApiImpl(InvoicePaymentService service) {
        this.service = service;
    }

    @Override
    public List<InvoicePayment> list(Reference<Invoice> invoiceReference, Instant updatedSince) {
        return Common.collect((page, perPage) -> list(invoiceReference, updatedSince, page, perPage));
    }

    @Override
    public Pagination<InvoicePayment> list(Reference<Invoice> invoiceReference, Instant updatedSince,
            int page, int perPage) {
        log.debug("Getting page {} of InvoicePayment list", page);

        Call<PaginatedList> call = service.list(invoiceReference.getId(), updatedSince, page, perPage);

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getInvoicePayments());
    }

    @Override
    public InvoicePayment create(Reference<Invoice> invoiceReference, InvoicePayment creationInfo) {
        Call<InvoicePayment> call = service.create(invoiceReference.getId(), creationInfo);
        InvoicePayment invoicePayment = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", invoicePayment);
        return invoicePayment;
    }

    @Override
    public void delete(Reference<Invoice> invoiceReference,
            Reference<InvoicePayment> invoicePaymentReference) {
        log.debug("Deleting {} in Invoice {}", invoicePaymentReference, invoiceReference);
        Call<Void> call = service.delete(invoiceReference.getId(), invoicePaymentReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
