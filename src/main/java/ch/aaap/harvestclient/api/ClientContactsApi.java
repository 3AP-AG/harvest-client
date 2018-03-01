package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ClientContactFilter;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ClientContactUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(Api.Role.ADMIN)
public interface ClientContactsApi extends Api.Simple<ClientContact> {

    /**
     * Return a list of clientContacts, sorted by creation date, newest first. Use
     * the filter object to filter the list.
     * 
     * @param filter
     *            filtering options
     * @return a (filtered) list of ClientContacts
     */
    List<ClientContact> list(ClientContactFilter filter);

    /**
     * Return a list of clientContacts, sorted by creation date, newest first. Use
     * the filter object to filter the list. Page and perPage allow controlling how
     * many results to return.
     *
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of ClientContacts
     */
    Pagination<ClientContact> list(ClientContactFilter filter, int page, int perPage);

    /**
     * @param clientContactReference
     *            a reference to an existing ClientContact
     * @return Return a full ClientContact object
     */
    @Override
    ClientContact get(Reference<ClientContact> clientContactReference);

    /**
     * Create a new ClientContact according to given creation information.
     *
     * @param clientContactCreationInfo
     *            the creation options
     * @return the newly created ClientContact
     */
    @Override
    ClientContact create(ClientContact clientContactCreationInfo);

    /**
     * Updates an existing ClientContact with the properties set in
     * ClientContactUpdateInfo
     *
     * @param clientContactReference
     *            the existing clientContact to be updated
     * @param toChange
     *            the properties to be updated
     * @return the updated ClientContact
     */
    ClientContact update(Reference<ClientContact> clientContactReference, ClientContactUpdateInfo toChange);

    /**
     * Delete an existing ClientContact.
     * 
     * @param clientContactReference
     *            a reference to an existing ClientContact to be deleted
     */
    @Override
    void delete(Reference<ClientContact> clientContactReference);
}
