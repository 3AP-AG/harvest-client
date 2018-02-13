package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.pagination.PaginatedClient;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface ClientService {

    String basePath = "clients";
    String id = "clientId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedClient> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<Client> get(@Path(id) long clientId);

    @POST(basePath)
    Call<Client> create(@Body Client creationInfo);

    @PATCH(path)
    Call<Client> update(@Path(id) long clientId, @Body ClientUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long clientId);
}
