package ch.aaap.harvestclient.api.filter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TaskAssignmentListFilter implements ListFilter {

    /**
     * Pass true to only return active task assignments and false to return inactive
     * task assignments.
     */
    private Boolean active;

    /**
     * Only return task assignments that have been updated since the given date and
     * time.
     */
    private Instant updatedSince;

    @Override
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
}
