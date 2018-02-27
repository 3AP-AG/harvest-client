package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.InvoiceItemCategoriesApi;
import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.InvoiceItemCategoryService;
import retrofit2.Call;

public class InvoiceItemCategoriesApiImpl implements InvoiceItemCategoriesApi {

    private static final Logger log = LoggerFactory.getLogger(InvoiceItemCategoriesApiImpl.class);
    private final InvoiceItemCategoryService service;

    public InvoiceItemCategoriesApiImpl(InvoiceItemCategoryService service) {
        this.service = service;
    }

    @Override
    public List<InvoiceItem.Category> list(@Nullable Instant updatedSince) {
        return Common.collect((page, perPage) -> this.list(updatedSince, page, perPage));
    }

    @Override
    public Pagination<InvoiceItem.Category> list(@Nullable Instant updatedSince, int page, int perPage) {
        log.debug("Getting page {} of InvoiceItem.Category list", page);

        Call<PaginatedList> call = service.list(updatedSince, page, perPage);

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);

        return Pagination.of(pagination, pagination.getInvoiceItemCategories());
    }

    @Override
    public InvoiceItem.Category get(Reference<InvoiceItem.Category> categoryReference) {
        Call<InvoiceItem.Category> call = service.get(categoryReference.getId());
        InvoiceItem.Category category = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", category);
        return category;
    }

    @Override
    public InvoiceItem.Category create(InvoiceItem.Category creationInfo) {
        Call<InvoiceItem.Category> call = service.create(creationInfo);
        InvoiceItem.Category category = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", category);
        return category;
    }

    @Override
    public InvoiceItem.Category update(Reference<InvoiceItem.Category> categoryReference,
            InvoiceItem.Category toChange) {
        log.debug("Updating {} with {}", categoryReference, toChange);
        Call<InvoiceItem.Category> call = service.update(categoryReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<InvoiceItem.Category> categoryReference) {
        log.debug("Deleting {}", categoryReference);
        Call<Void> call = service.delete(categoryReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
