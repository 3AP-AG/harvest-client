package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.api.filter.EstimateFilter;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateLineItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.EstimateUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.EstimateService;
import retrofit2.Call;

public class EstimatesApiImpl implements EstimatesApi {

    private static final Logger log = LoggerFactory.getLogger(EstimatesApiImpl.class);

    private final EstimateService service;

    public EstimatesApiImpl(EstimateService service) {
        this.service = service;
    }

    @Override
    public List<Estimate> list(EstimateFilter filter) {
        return Common.collect((page, perPage) -> this.list(filter, page, perPage));
    }

    @Override
    public Pagination<Estimate> list(EstimateFilter filter, int page, int perPage) {

        log.debug("Getting page {} of estimate list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getEstimates());
    }

    @Override
    public Estimate get(Reference<Estimate> estimateReference) {
        Call<Estimate> call = service.get(estimateReference.getId());
        Estimate estimate = ExceptionHandler.callOrThrow(call);
        log.debug("Got Estimate {}", estimate);
        return estimate;
    }

    @Override
    public Estimate create(Estimate estimateCreationInfo) {
        Call<Estimate> call = service.create(estimateCreationInfo);
        Estimate estimate = ExceptionHandler.callOrThrow(call);
        log.debug("Created Estimate {}", estimate);
        return estimate;
    }

    @Override
    public Estimate addLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem) {
        return null;
    }

    @Override
    public Estimate updateLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem) {
        return null;
    }

    @Override
    public Estimate deleteLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem) {
        return null;
    }

    @Override
    public Estimate update(Reference<Estimate> estimateReference, EstimateUpdateInfo toChange) {
        log.debug("Updating estimate {} with {}", estimateReference, toChange);
        Call<Estimate> call = service.update(estimateReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Estimate> estimateReference) {

        log.debug("Deleting Estimate {}", estimateReference);
        Call<Void> call = service.delete(estimateReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
