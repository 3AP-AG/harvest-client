package ch.aaap.harvestclient.api.filter;

import java.util.Map;

/**
 * Interface implemented by all Filters
 */
public interface ListFilter {

    Map<String, Object> toMap();

    default Map<String, Object> toMap(int page, int perPage) {
        Map<String, Object> filterMap = toMap();
        // add pagination settings
        filterMap.put("page", page);
        filterMap.put("per_page", perPage);

        return filterMap;
    }
}
