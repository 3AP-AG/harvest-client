package ch.aaap.harvestclient.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.domain.pagination.Pagination;

/**
 * Shared implementation of ApiImpl classes
 */
class Common {

    private static final Logger log = LoggerFactory.getLogger(Common.class);

    /**
     * Default page size to request. Maximum for the API is 100.
     */
    private static final int PER_PAGE = 100;

    /**
     * Implement the collection of a full list of paginated T, given a function to
     * get a single page of them
     * 
     * @param supplier
     *            function that takes page and perPage and return a Pagination of T
     * @param <T>
     *            a domain objects, e.g. User
     * @return a list of all pages of T
     */
    public static <T> List<T> collect(BiFunction<Integer, Integer, Pagination<T>> supplier) {
        Integer nextPage = 1;

        List<T> result = new ArrayList<>();

        while (nextPage != null) {

            Pagination<T> paginatedContacts = supplier.apply(nextPage, PER_PAGE);

            result.addAll(paginatedContacts.getList());
            nextPage = paginatedContacts.getNextPage();
        }

        log.debug("Listed {} objects: {}", result.size(), result);

        return result;
    }

}
