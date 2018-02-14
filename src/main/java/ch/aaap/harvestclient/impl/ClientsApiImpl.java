package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ClientsApi;
import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ClientService;
import retrofit2.Call;

public class ClientsApiImpl implements ClientsApi {

    private static final Logger log = LoggerFactory.getLogger(ClientsApiImpl.class);
    private static final int PER_PAGE = 100;
    private final ClientService service;

    public ClientsApiImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public List<Client> list(ClientFilter filter) {

        Integer nextPage = 1;

        List<Client> result = new ArrayList<>();

        while (nextPage != null)

        {
            log.debug("Getting page {} of Client list", nextPage);

            Map<String, Object> filterMap = filter.toMap();
            // add pagination settings
            filterMap.put("page", nextPage);
            filterMap.put("per_page", PER_PAGE);

            Call<PaginatedList> call = service.list(filterMap);

            PaginatedList pagination = ExceptionHandler.callOrThrow(call);

            result.addAll(pagination.getClients());
            nextPage = pagination.getNextPage();
        }

        log.debug("Listed {} Client: {}", result.size(), result);

        return result;
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
