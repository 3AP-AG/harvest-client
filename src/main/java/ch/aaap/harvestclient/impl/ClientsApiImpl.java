package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ClientService;
import retrofit2.Call;

public class ClientsApiImpl implements ClientsApi {

    private static final Logger log = LoggerFactory.getLogger(ClientsApiImpl.class);
    private final ClientService service;

    public ClientsApiImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public List<Client> list(ClientFilter filter) {
        return Common.collect((page, perPage) -> this.list(filter, page, perPage));
    }

    @Override
    public Pagination<Client> list(ClientFilter filter, int page, int perPage) {
        log.debug("Getting page {} of Client list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);

        return Pagination.of(pagination, pagination.getClients());
    }

    @Override
    public Client get(Reference<Client> clientReference) {
        Call<Client> call = service.get(clientReference.getId());
        Client client = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", client);
        return client;
    }

    @Override
    public Client create(Client creationInfo) {
        Call<Client> call = service.create(creationInfo);
        Client client = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", client);
        return client;
    }

    @Override
    public Client update(Reference<Client> clientReference, ClientUpdateInfo toChange) {
        log.debug("Updating {} with {}", clientReference, toChange);
        Call<Client> call = service.update(clientReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Client> clientReference) {
        log.debug("Deleting {}", clientReference);
        Call<Void> call = service.delete(clientReference.getId());
        ExceptionHandler.callOrThrow(call);
    }
}
