package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface ClientsApi extends Api.Simple<Client> {

    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all Clients in the account, sorted by creation date, newest
     *         first.
     */
    List<Client> list(ClientFilter filter);

    /**
     * Return a list of clients, sorted by creation date, newest first. Use the
     * filter object to filter the list. Page and perPage allow controlling how many
     * results to return.
     *
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of Client
     */
    Pagination<Client> list(ClientFilter filter, int page, int perPage);

    /**
     * Return an existing Client.
     *
     * @param clientReference
     *            a reference to an existing Client
     * @return the full Client object
     */
    @Override
    Client get(Reference<Client> clientReference);

    /**
     * Create a new Client
     *
     * @param creationInfo
     *            creation information
     * @return the created Client
     */
    @Override
    Client create(Client creationInfo);

    /**
     * Updates the specific client by setting the values of the parameters passed.
     * Any parameters not provided will be left unchanged
     *
     * @param clientReference
     *            An existing Client to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated Client
     */
    Client update(Reference<Client> clientReference, ClientUpdateInfo toChange);

    /**
     * Delete an existing Client.
     *
     * @param clientReference
     *            a reference to the Client to be deleted
     */
    @Override
    void delete(Reference<Client> clientReference);

}
