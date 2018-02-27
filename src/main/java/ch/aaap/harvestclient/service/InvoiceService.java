package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.InvoiceUpdateInfo;
import ch.aaap.harvestclient.domain.param.LineItemContainer;
import retrofit2.Call;
import retrofit2.http.*;

public interface InvoiceService {

    String basePath = "invoices";
    String id = "id";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<Invoice> get(@Path(id) long invoiceId);

    @POST(basePath)
    Call<Invoice> create(@Body Object creationInfo);

    @PATCH(path)
    Call<Invoice> update(@Path(id) long invoiceId, @Body InvoiceUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long invoiceId);

    @PATCH(path)
    Call<Invoice> updateItem(@Path(id) long invoiceId, @Body LineItemContainer updateInfo);
}
