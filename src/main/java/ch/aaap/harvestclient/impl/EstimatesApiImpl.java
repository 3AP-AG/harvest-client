package ch.aaap.harvestclient.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.api.filter.EstimateFilter;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.*;
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
    public Estimate addLineItem(Reference<Estimate> estimateReference, EstimateItem creationInfo) {
        log.debug("Adding item {} to {}", creationInfo, estimateReference);
        EstimateUpdateInfo changes = ImmutableEstimateUpdateInfo.builder()
                .addEstimateItem(creationInfo)
                .build();
        Call<Estimate> call = service.update(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public Estimate updateLineItem(Reference<Estimate> estimateReference,
            Reference<EstimateItem> estimateItemReference, EstimateItemUpdateInfo updateInfo) {
        log.debug("Updating estimate {}, item {} with {}", estimateReference, estimateItemReference, updateInfo);

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addLineItem(ImmutableEstimateItemUpdateInfo.builder()
                        .from(updateInfo)
                        .id(estimateItemReference.getId())
                        .build())
                .build();

        Call<Estimate> call = service.updateItem(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public Estimate deleteLineItem(Reference<Estimate> estimateReference,
            Reference<EstimateItem> estimateItemReference) {
        log.debug("Deleting {} in estimate {}", estimateItemReference, estimateReference);

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addLineItem(ImmutableLineItemDeleteInfo.of(estimateItemReference.getId()))
                .build();

        Call<Estimate> call = service.updateItem(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Estimate addLineItems(Reference<Estimate> estimateReference, List<EstimateItem> creationInfoList) {
        log.debug("Adding items {} to {}", creationInfoList, estimateReference);
        EstimateUpdateInfo changes = ImmutableEstimateUpdateInfo.builder()
                .addAllEstimateItems(creationInfoList)
                .build();
        Call<Estimate> call = service.update(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Estimate updateLineItems(Reference<Estimate> estimateReference,
            List<? extends Reference<EstimateItem>> estimateItemReferenceList,
            List<EstimateItemUpdateInfo> updateInfoList) {

        log.debug("Updating estimate {}, item {} with {}", estimateReference, estimateItemReferenceList,
                updateInfoList);

        estimateItemReferenceList.forEach(Reference::requireId);
        updateInfoList.forEach(Objects::requireNonNull);

        List<EstimateItemUpdateInfo> updateInfoListWithId = IntStream
                .range(0, estimateItemReferenceList.size())
                .mapToObj(i -> ImmutableEstimateItemUpdateInfo.builder()
                        .from(updateInfoList.get(i))
                        .id(estimateItemReferenceList.get(i).getId())
                        .build())
                .collect(Collectors.toList());

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addAllLineItems(updateInfoListWithId)
                .build();

        Call<Estimate> call = service.updateItem(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public Estimate deleteLineItems(Reference<Estimate> estimateReference,
            List<? extends Reference<EstimateItem>> estimateItemReferenceList) {
        log.debug("Deleting items {} from estimate {}", estimateItemReferenceList, estimateReference);

        estimateItemReferenceList.forEach(Objects::requireNonNull);

        List<LineItemDeleteInfo> deleteList = estimateItemReferenceList.stream()
                .map(ref -> ImmutableLineItemDeleteInfo.of(ref.getId()))
                .collect(Collectors.toList());

        LineItemContainer changes = ImmutableLineItemContainer.builder()
                .addAllLineItems(deleteList)
                .build();

        Call<Estimate> call = service.updateItem(estimateReference.getId(), changes);
        return ExceptionHandler.callOrThrow(call);
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
