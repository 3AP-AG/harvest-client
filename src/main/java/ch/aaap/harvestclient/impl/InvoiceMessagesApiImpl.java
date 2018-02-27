package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.InvoiceMessagesApi;
import ch.aaap.harvestclient.domain.ImmutableInvoiceMessage;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoiceMessage;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.InvoiceMessagesService;
import retrofit2.Call;

public class InvoiceMessagesApiImpl implements InvoiceMessagesApi {

    private static final Logger log = LoggerFactory.getLogger(InvoiceMessagesApiImpl.class);

    private final InvoiceMessagesService service;

    public InvoiceMessagesApiImpl(InvoiceMessagesService service) {
        this.service = service;
    }

    @Override
    public List<InvoiceMessage> list(Reference<Invoice> invoiceReference, Instant updatedSince) {
        return Common.collect((page, perPage) -> list(invoiceReference, updatedSince, page, perPage));
    }

    @Override
    public Pagination<InvoiceMessage> list(Reference<Invoice> invoiceReference, Instant updatedSince,
            int page, int perPage) {
        log.debug("Getting page {} of InvoiceMessage list", page);

        Call<PaginatedList> call = service.list(invoiceReference.getId(), updatedSince, page, perPage);

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getInvoiceMessages());
    }

    @Override
    public InvoiceMessage get(Reference<Invoice> invoiceReference,
            Reference<InvoiceMessage> invoiceMessageReference) {
        Call<InvoiceMessage> call = service.get(invoiceReference.getId(), invoiceMessageReference.getId());
        InvoiceMessage invoiceMessage = ExceptionHandler.callOrThrow(call);
        log.debug("Gotten {}", invoiceMessage);
        return invoiceMessage;
    }

    @Override
    public InvoiceMessage create(Reference<Invoice> invoiceReference, InvoiceMessage creationInfo) {
        Call<InvoiceMessage> call = service.create(invoiceReference.getId(), creationInfo);
        InvoiceMessage invoiceMessage = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", invoiceMessage);
        return invoiceMessage;
    }

    @Override
    public InvoiceMessage markAs(Reference<Invoice> invoiceReference, InvoiceMessage.EventType eventType) {

        log.debug("Marking invoice {} as {}", invoiceReference, eventType);
        Call<InvoiceMessage> call = service.create(invoiceReference.getId(), ImmutableInvoiceMessage.builder()
                .eventType(eventType)
                .build());
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Invoice> invoiceReference,
            Reference<InvoiceMessage> invoiceMessageReference) {
        log.debug("Deleting {} in Invoice {}", invoiceMessageReference, invoiceReference);
        Call<Void> call = service.delete(invoiceReference.getId(), invoiceMessageReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
