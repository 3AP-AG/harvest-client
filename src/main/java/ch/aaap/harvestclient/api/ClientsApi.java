package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ClientFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.param.ClientCreationInfo;
import ch.aaap.harvestclient.domain.param.ClientUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface ClientsApi {

    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all Clients in the account, sorted by creation date, newest
     *         first.
     */
    List<Client> list(ClientFilter filter);

    /**
     * Return an existing Client.
     *
     * @param clientReference
     *            a reference to an existing Client
     * @return the full Client object
     */
    Client get(Reference<Client> clientReference);

    /**
     * Create a new Client
     *
     * @param creationInfo
     *            creation information
     * @return the created Client
     */
    Client create(ClientCreationInfo creationInfo);

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
    void delete(Reference<Client> clientReference);

}
