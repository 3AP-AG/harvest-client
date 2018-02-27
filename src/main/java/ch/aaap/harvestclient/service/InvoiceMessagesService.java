package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.InvoiceMessage;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.*;

public interface InvoiceMessagesService {

    String messageId = "id";
    String invId = "invoiceId";
    String basePath = "invoices/{" + invId + "}/messages";
    String path = basePath + "/{" + messageId + "}";

    @GET(basePath)
    Call<PaginatedList> list(@Path(invId) long invoiceId, @Query("updated_since") Instant updatedSince,
            @Query("page") int page, @Query("per_page") int perPage);

    @GET(path)
    Call<InvoiceMessage> get(@Path(invId) long invoiceId, @Path(messageId) long invoiceMessageId);

    @POST(basePath)
    Call<InvoiceMessage> create(@Path(invId) long invoiceId, @Body InvoiceMessage creationInfo);

    @DELETE(path)
    Call<Void> delete(@Path(invId) long invoiceId, @Path(messageId) long invoiceMessageId);

}
