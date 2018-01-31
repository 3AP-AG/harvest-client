package ch.aaap.harvestclient.api.filter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import ch.aaap.harvestclient.domain.reference.ClientReference;
import ch.aaap.harvestclient.domain.reference.ProjectReference;
import ch.aaap.harvestclient.domain.reference.UserReference;

/**
 * Contains all possible ways to filter a timeentries list. Comments on
 * individual fields comes from the Harvest v2 API documentiation
 * 
 */
public class TimeEntryListFilter {

    /**
     * Only return time entries belonging to given user.
     */
    private UserReference userReference;

    /**
     * Only return time entries belonging to the given client.
     */
    private ClientReference clientReference;

    /**
     * Only return time entries belonging to the given project.
     */
    private ProjectReference projectReference;

    /**
     * Pass true to only return time entries that have been invoiced and false to
     * return time entries that have not been invoiced.
     */
    private Boolean billed;
    /**
     * Pass true to only return running time entries and false to return non-running
     * time entries.
     */
    private Boolean running;

    /**
     * Only return time entries that have been updated since the given date and
     * time.
     */
    private Instant updatedSince;

    /**
     * Only return time entries with a spent_date on or after the given date.
     */
    private Instant from;

    /**
     * Only return time entries with a spent_date on or after the given date.
     */
    private Instant to;

    public static TimeEntryListFilter emptyFilter() {
        return new TimeEntryListFilter();
    }

    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();

        if (userReference != null) {
            map.put("user_id", userReference.getId());
        }
        if (clientReference != null) {
            map.put("client_id", clientReference.getId());
        }
        if (projectReference != null) {
            map.put("project_id", projectReference.getId());
        }
        if (billed != null) {
            map.put("is_billed", billed);
        }
        if (running != null) {
            map.put("is_running", running);
        }
        if (updatedSince != null) {
            map.put("updated_since", updatedSince);
        }
        if (from != null) {
            map.put("from", from);
        }
        if (to != null) {
            map.put("to", to);
        }
        return map;
    }

    public UserReference getUserReference() {
        return userReference;
    }

    public void setUserReference(UserReference userReference) {
        this.userReference = userReference;
    }

    public ClientReference getClientReference() {
        return clientReference;
    }

    public void setClientReference(ClientReference clientReference) {
        this.clientReference = clientReference;
    }

    public ProjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ProjectReference projectReference) {
        this.projectReference = projectReference;
    }

    public Boolean getBilled() {
        return billed;
    }

    public void setBilled(Boolean billed) {
        this.billed = billed;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Instant getUpdatedSince() {
        return updatedSince;
    }

    public void setUpdatedSince(Instant updatedSince) {
        this.updatedSince = updatedSince;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return to;
    }

    public void setTo(Instant to) {
        this.to = to;
    }
}
