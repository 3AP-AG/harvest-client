package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ClientContactsApi;
import ch.aaap.harvestclient.api.filter.ClientContactFilter;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ClientContactUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ClientContactService;
import retrofit2.Call;

public class ClientContactsApiImpl implements ClientContactsApi {

    private static final Logger log = LoggerFactory.getLogger(ClientContactsApiImpl.class);

    private final ClientContactService service;

    public ClientContactsApiImpl(ClientContactService service) {
        this.service = service;
    }

    @Override
    public List<ClientContact> list(ClientContactFilter filter) {
        return Common.collect((page, perPage) -> this.list(filter, page, perPage));
    }

    @Override
    public Pagination<ClientContact> list(ClientContactFilter filter, int page, int perPage) {

        log.debug("Getting page {} of clientContact list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getContacts());
    }

    @Override
    public ClientContact get(Reference<ClientContact> clientContactReference) {
        Call<ClientContact> call = service.get(clientContactReference.getId());
        ClientContact clientContact = ExceptionHandler.callOrThrow(call);
        log.debug("Got ClientContact {}", clientContact);
        return clientContact;
    }

    @Override
    public ClientContact create(ClientContact clientContactCreationInfo) {
        Call<ClientContact> call = service.create(clientContactCreationInfo);
        ClientContact clientContact = ExceptionHandler.callOrThrow(call);
        log.debug("Created ClientContact {}", clientContact);
        return clientContact;
    }

    @Override
    public ClientContact update(Reference<ClientContact> clientContactReference, ClientContactUpdateInfo toChange) {
        log.debug("Updating clientContact {} with {}", clientContactReference, toChange);
        Call<ClientContact> call = service.update(clientContactReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<ClientContact> clientContactReference) {

        log.debug("Deleting ClientContact {}", clientContactReference);
        Call<Void> call = service.delete(clientContactReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
