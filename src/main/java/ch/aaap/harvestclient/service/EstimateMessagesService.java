package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.EstimateMessage;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.*;

public interface EstimateMessagesService {

    String messageId = "id";
    String estId = "estimateId";
    String basePath = "estimates/{" + estId + "}/messages";
    String path = basePath + "/{" + messageId + "}";

    @GET(basePath)
    Call<PaginatedList> list(@Path(estId) long estimateId, @Query("updated_since") Instant updatedSince,
            @Query("page") int page, @Query("per_page") int perPage);

    @GET(path)
    Call<EstimateMessage> get(@Path(estId) long estimateId, @Path(messageId) long estimateMessageId);

    @POST(basePath)
    Call<EstimateMessage> create(@Path(estId) long estimateId, @Body EstimateMessage creationInfo);

    @DELETE(path)
    Call<Void> delete(@Path(estId) long estimateId, @Path(messageId) long estimateMessageId);

}
