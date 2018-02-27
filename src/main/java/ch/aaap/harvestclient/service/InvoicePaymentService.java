package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.InvoicePayment;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.*;

public interface InvoicePaymentService {
    String paymentId = "id";
    String estId = "invoiceId";
    String basePath = "invoices/{" + estId + "}/payments";
    String path = basePath + "/{" + paymentId + "}";

    @GET(basePath)
    Call<PaginatedList> list(@Path(estId) long invoiceId, @Query("updated_since") Instant updatedSince,
            @Query("page") int page, @Query("per_page") int perPage);

    @POST(basePath)
    Call<InvoicePayment> create(@Path(estId) long invoiceId, @Body InvoicePayment creationInfo);

    @DELETE(path)
    Call<Void> delete(@Path(estId) long invoiceId, @Path(paymentId) long invoicePaymentId);

}
