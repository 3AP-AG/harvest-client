package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.ClientContact;

public class PaginatedClientContact extends PaginatedList {

    private List<ClientContact> contacts;

    public List<ClientContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<ClientContact> contacts) {
        this.contacts = contacts;
    }
}
