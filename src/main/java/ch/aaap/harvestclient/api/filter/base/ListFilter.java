package ch.aaap.harvestclient.api.filter.base;

import java.util.Map;

/**
 * Interface implemented by all Filters
 */
public interface ListFilter {

    Map<String, Object> toMap();

    /*
     * Note: it would be nicer to just pass the filter objects to Retrofit, but it
     * does not support something like @QueryObject, see
     * https://github.com/square/retrofit/issues/1503
     */
    default Map<String, Object> toMap(int page, int perPage) {
        Map<String, Object> filterMap = toMap();
        // add pagination settings
        filterMap.put("page", page);
        filterMap.put("per_page", perPage);

        return filterMap;
    }
}
