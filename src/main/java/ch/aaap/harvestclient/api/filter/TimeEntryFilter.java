package ch.aaap.harvestclient.api.filter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Contains all possible ways to filter a timeentries list. Comments on
 * individual fields comes from the Harvest v2 API documentiation
 * 
 */
public class TimeEntryFilter {

    /**
     * Only return time entries belonging to given user.
     */
    private Reference<User> userReference;

    /**
     * Only return time entries belonging to the given client.
     */
    private Reference<Client> clientReference;

    /**
     * Only return time entries belonging to the given project.
     */
    private Reference<Project> projectReference;

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
    private LocalDate from;

    /**
     * Only return time entries with a spent_date on or after the given date.
     */
    private LocalDate to;

    public static TimeEntryFilter emptyFilter() {
        return new TimeEntryFilter();
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

    public Reference<User> getUserReference() {
        return userReference;
    }

    public void setUserReference(Reference<User> userReference) {
        this.userReference = userReference;
    }

    public Reference<Client> getClientReference() {
        return clientReference;
    }

    public void setClientReference(Reference<Client> clientReference) {
        this.clientReference = clientReference;
    }

    public Reference<Project> getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(Reference<Project> projectReference) {
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

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
