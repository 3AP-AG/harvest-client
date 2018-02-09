package ch.aaap.harvestclient.api.filter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ActiveAndUpdatedFilter {

    /**
     * Pass true to only return active objects and false to return inactive objects.
     */
    private Boolean active;

    /**
     * Only return objects that have been updated since the given date and time.
     */
    private Instant updatedSince;

    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();

        if (active != null) {
            map.put("is_active", active);
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

    public Instant getUpdatedSince() {
        return updatedSince;
    }

    public void setUpdatedSince(Instant updatedSince) {
        this.updatedSince = updatedSince;
    }
}
