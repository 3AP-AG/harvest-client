package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.EstimateItemCategoriesApi;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.EstimateItemCategoryService;
import retrofit2.Call;

public class EstimateItemCategoriesApiImpl implements EstimateItemCategoriesApi {

    private static final Logger log = LoggerFactory.getLogger(EstimateItemCategoriesApiImpl.class);
    private final EstimateItemCategoryService service;

    public EstimateItemCategoriesApiImpl(EstimateItemCategoryService service) {
        this.service = service;
    }

    @Override
    public List<EstimateItem.Category> list(@Nullable Instant updatedSince) {
        return Common.collect((page, perPage) -> this.list(updatedSince, page, perPage));
    }

    @Override
    public Pagination<EstimateItem.Category> list(@Nullable Instant updatedSince, int page, int perPage) {
        log.debug("Getting page {} of EstimateItem.Category list", page);

        Call<PaginatedList> call = service.list(updatedSince, page, perPage);

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);

        return Pagination.of(pagination, pagination.getEstimateItemCategories());
    }

    @Override
    public EstimateItem.Category get(Reference<EstimateItem.Category> categoryReference) {
        Call<EstimateItem.Category> call = service.get(categoryReference.getId());
        EstimateItem.Category category = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", category);
        return category;
    }

    @Override
    public EstimateItem.Category create(EstimateItem.Category creationInfo) {
        Call<EstimateItem.Category> call = service.create(creationInfo);
        EstimateItem.Category category = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", category);
        return category;
    }

    @Override
    public EstimateItem.Category update(Reference<EstimateItem.Category> categoryReference,
            EstimateItem.Category toChange) {
        log.debug("Updating {} with {}", categoryReference, toChange);
        Call<EstimateItem.Category> call = service.update(categoryReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<EstimateItem.Category> categoryReference) {
        log.debug("Deleting {}", categoryReference);
        Call<Void> call = service.delete(categoryReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
