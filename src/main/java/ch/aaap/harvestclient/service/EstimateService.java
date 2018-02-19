package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.EstimateUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface EstimateService {
    String basePath = "estimates";
    String id = "id";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<Estimate> get(@Path(id) long estimateId);

    @POST(basePath)
    Call<Estimate> create(@Body Estimate creationInfo);

    @PATCH(path)
    Call<Estimate> update(@Path(id) long estimateId, @Body EstimateUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long estimateId);
}
