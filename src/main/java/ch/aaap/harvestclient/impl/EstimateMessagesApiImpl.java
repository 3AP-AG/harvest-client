package ch.aaap.harvestclient.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.EstimateMessagesApi;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateMessage;
import ch.aaap.harvestclient.domain.ImmutableEstimateMessage;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.EstimateMessagesService;
import retrofit2.Call;

public class EstimateMessagesApiImpl implements EstimateMessagesApi {

    private static final Logger log = LoggerFactory.getLogger(EstimateMessagesApiImpl.class);

    private final EstimateMessagesService service;

    public EstimateMessagesApiImpl(EstimateMessagesService service) {
        this.service = service;
    }

    @Override
    public List<EstimateMessage> list(Reference<Estimate> estimateReference, Instant updatedSince) {
        return Common.collect((page, perPage) -> list(estimateReference, updatedSince, page, perPage));
    }

    @Override
    public Pagination<EstimateMessage> list(Reference<Estimate> estimateReference, Instant updatedSince,
            int page, int perPage) {
        log.debug("Getting page {} of EstimateMessage list", page);

        Call<PaginatedList> call = service.list(estimateReference.getId(), updatedSince, page, perPage);

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getEstimateMessages());
    }

    @Override
    public EstimateMessage create(Reference<Estimate> estimateReference, EstimateMessage creationInfo) {
        Call<EstimateMessage> call = service.create(estimateReference.getId(), creationInfo);
        EstimateMessage estimateMessage = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", estimateMessage);
        return estimateMessage;
    }

    @Override
    public EstimateMessage markAs(Reference<Estimate> estimateReference, EstimateMessage.EventType eventType) {

        log.debug("Marking estimate {} as {}", estimateReference, eventType);
        Call<EstimateMessage> call = service.create(estimateReference.getId(), ImmutableEstimateMessage.builder()
                .eventType(eventType)
                .build());
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Estimate> estimateReference,
            Reference<EstimateMessage> estimateMessageReference) {
        log.debug("Deleting {} in Estimate {}", estimateMessageReference, estimateReference);
        Call<Void> call = service.delete(estimateReference.getId(), estimateMessageReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
