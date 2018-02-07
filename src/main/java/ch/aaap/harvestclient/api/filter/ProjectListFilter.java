package ch.aaap.harvestclient.api.filter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import ch.aaap.harvestclient.domain.reference.ClientReference;

/**
 * Ways to filter a Project list. Not setting a field means no filtering on the
 * field will be done.
 */
public class ProjectListFilter {

    /**
     * Pass true to only return active projects and false to return inactive
     * projects.
     */
    private Boolean active;

    /**
     * Only return projects belonging to the client with the given ID.
     */
    private ClientReference clientReference;

    /**
     * Only return projects that have been updated since the given date and time.
     */
    private Instant updatedSince;

    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();

        if (active != null) {
            map.put("is_active", active);
        }
        if (clientReference != null) {
            map.put("client_id", clientReference.getId());
        }
        if (updatedSince != null) {
            map.put("updated_since", updatedSince);
        }

        return map;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ClientReference getClientReference() {
        return clientReference;
    }

    public void setClientReference(ClientReference clientReference) {
        this.clientReference = clientReference;
    }

    public Instant getUpdatedSince() {
        return updatedSince;
    }

    public void setUpdatedSince(Instant updatedSince) {
        this.updatedSince = updatedSince;
    }

    @Override
    public String toString() {
        return "ProjectListFilter{" +
                "active=" + active +
                ", clientReference=" + clientReference +
                ", updatedSince=" + updatedSince +
                '}';
    }

    public static ProjectListFilter emptyFilter() {
        return new ProjectListFilter();
    }
}