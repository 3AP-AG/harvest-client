package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.*;

public interface InvoiceItemCategoryService {

    String basePath = "invoice_item_categories";
    String id = "categoryId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@Query("updated_since") Instant updatedSince, @Query("page") int page,
            @Query("per_page") int perPage);

    @GET(path)
    Call<InvoiceItem.Category> get(@Path(id) long categoryId);

    @POST(basePath)
    Call<InvoiceItem.Category> create(@Body InvoiceItem.Category creationInfo);

    @PATCH(path)
    Call<InvoiceItem.Category> update(@Path(id) long categoryId, @Body InvoiceItem.Category updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long categoryId);
}
