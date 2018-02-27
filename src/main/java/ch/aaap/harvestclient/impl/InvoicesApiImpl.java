package ch.aaap.harvestclient.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.api.filter.InvoiceFilter;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.InvoiceService;
import retrofit2.Call;

public class InvoicesApiImpl implements InvoicesApi {

    private static final Logger log = LoggerFactory.getLogger(InvoicesApiImpl.class);

    private final InvoiceService service;

    public InvoicesApiImpl(InvoiceService service) {
        this.service = service;
    }

    @Override
    public List<Invoice> list(InvoiceFilter filter) {
        return Common.collect((page, perPage) -> this.list(filter, page, perPage));
    }

    @Override
    public Pagination<Invoice> list(InvoiceFilter filter, int page, int perPage) {

        log.debug("Getting page {} of invoice list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getInvoices());
    }

    @Override
    public Invoice get(Reference<Invoice> invoiceReference) {
        Call<Invoice> call = service.get(invoiceReference.getId());
        Invoice invoice = ExceptionHandler.callOrThrow(call);
        log.debug("Got Invoice {}", invoice);
        return invoice;
    }

    @Override
    public Invoice create(Invoice invoiceCreationInfo) {
        Call<Invoice> call = service.create(invoiceCreationInfo);
        Invoice invoice = ExceptionHandler.callOrThrow(call);
        log.debug("Created Invoice {}", invoice);
        return invoice;
    }

    @Override
    public Invoice createFrom(InvoiceImportInfo invoiceCreationInfo) {
        Call<Invoice> call = service.create(invoiceCreationInfo);
        Invoice invoice = ExceptionHandler.callOrThrow(call);
        log.debug("Created Invoice {}", invoice);
        return invoice;
    }

    @Override
    public Invoice addLineItem(Reference<Invoice> invoiceReference, InvoiceItem creationInfo) {
        log.debug("Adding item {} to {}", creationInfo, invoiceReference);
        InvoiceUpdateInfo changes = ImmutableInvoiceUpdateInfo.builder()
                .addInvoiceItem(creationInfo)
                .build();
        Call<Invoice> call = service.update(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public Invoice updateLineItem(Reference<Invoice> invoiceReference,
            Reference<InvoiceItem> invoiceItemReference, InvoiceItemUpdateInfo updateInfo) {
        log.debug("Updating invoice {}, item {} with {}", invoiceReference, invoiceItemReference, updateInfo);

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addLineItem(ImmutableInvoiceItemUpdateInfo.builder()
                        .from(updateInfo)
                        .id(invoiceItemReference.getId())
                        .build())
                .build();

        Call<Invoice> call = service.updateItem(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public Invoice deleteLineItem(Reference<Invoice> invoiceReference,
            Reference<InvoiceItem> invoiceItemReference) {
        log.debug("Deleting {} in invoice {}", invoiceItemReference, invoiceReference);

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addLineItem(ImmutableLineItemDeleteInfo.of(invoiceItemReference.getId()))
                .build();

        Call<Invoice> call = service.updateItem(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Invoice addLineItems(Reference<Invoice> invoiceReference, List<InvoiceItem> creationInfoList) {
        log.debug("Adding items {} to {}", creationInfoList, invoiceReference);
        InvoiceUpdateInfo changes = ImmutableInvoiceUpdateInfo.builder()
                .addAllInvoiceItems(creationInfoList)
                .build();
        Call<Invoice> call = service.update(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Invoice updateLineItems(Reference<Invoice> invoiceReference,
            List<? extends Reference<InvoiceItem>> invoiceItemReferenceList,
            List<InvoiceItemUpdateInfo> updateInfoList) {

        log.debug("Updating invoice {}, item {} with {}", invoiceReference, invoiceItemReferenceList,
                updateInfoList);

        invoiceItemReferenceList.forEach(Reference::requireId);
        updateInfoList.forEach(Objects::requireNonNull);

        List<InvoiceItemUpdateInfo> updateInfoListWithId = IntStream
                .range(0, invoiceItemReferenceList.size())
                .mapToObj(i -> ImmutableInvoiceItemUpdateInfo.builder()
                        .from(updateInfoList.get(i))
                        .id(invoiceItemReferenceList.get(i).getId())
                        .build())
                .collect(Collectors.toList());

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addAllLineItems(updateInfoListWithId)
                .build();

        Call<Invoice> call = service.updateItem(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Invoice deleteLineItems(Reference<Invoice> invoiceReference,
            List<? extends Reference<InvoiceItem>> invoiceItemReferenceList) {
        log.debug("Deleting items {} from invoice {}", invoiceItemReferenceList, invoiceReference);

        invoiceItemReferenceList.forEach(Objects::requireNonNull);

        List<LineItemDeleteInfo> deleteList = invoiceItemReferenceList.stream()
                .map(ref -> ImmutableLineItemDeleteInfo.of(ref.getId()))
                .collect(Collectors.toList());

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addAllLineItems(deleteList)
                .build();

        Call<Invoice> call = service.updateItem(invoiceReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Invoice update(Reference<Invoice> invoiceReference, InvoiceUpdateInfo toChange) {
        log.debug("Updating invoice {} with {}", invoiceReference, toChange);
        Call<Invoice> call = service.update(invoiceReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Invoice> invoiceReference) {

        log.debug("Deleting Invoice {}", invoiceReference);
        Call<Void> call = service.delete(invoiceReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
