package ch.aaap.harvestclient.service;

import java.time.Instant;

import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import retrofit2.Call;
import retrofit2.http.*;

public interface EstimateItemCategoryService {

    String basePath = "estimate_item_categories";
    String id = "categoryId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@Query("updated_since") Instant updatedSince, @Query("page") int page,
            @Query("per_page") int perPage);

    @GET(path)
    Call<EstimateItem.Category> get(@Path(id) long categoryId);

    @POST(basePath)
    Call<EstimateItem.Category> create(@Body EstimateItem.Category creationInfo);

    @PATCH(path)
    Call<EstimateItem.Category> update(@Path(id) long categoryId, @Body EstimateItem.Category updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long categoryId);
}
