package ch.aaap.harvestclient.api.filter.base;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ClientAndUpdatedFilter implements ListFilter {

    private Reference<Client> clientReference;

    private Instant updatedSince;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (clientReference != null) {
            map.put("client_id", clientReference.getId());
        }
        if (updatedSince != null) {
            map.put("updated_since", updatedSince);
        }
        return map;
    }

    public Reference<Client> getClientReference() {
        return clientReference;
    }

    public void setClientReference(
            Reference<Client> clientReference) {
        this.clientReference = clientReference;
    }

    public Instant getUpdatedSince() {
        return updatedSince;
    }

    public void setUpdatedSince(Instant updatedSince) {
        this.updatedSince = updatedSince;
    }
}
