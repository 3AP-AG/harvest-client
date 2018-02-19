package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.value.Value;

/**
 * A container for a partial list of objects, that are being returned paginated.
 * Has the pagination information needed to get the rest of the objects
 * 
 * @param <T>
 *            a domain type like User
 */
@Value.Immutable
public interface Pagination<T> {

    List<T> getList();

    int getPerPage();

    int getTotalPages();

    /**
     * Will be null once we have read the last page available
     * 
     * @return either the number of the next page or null if the list has ended
     */
    @Nullable
    Integer getNextPage();

    @Nullable
    Integer getPreviousPage();

    int getPage();

    static <T> Pagination<T> of(PaginatedList paginatedList, List<T> elements) {

        return ImmutablePagination.<T>builder()
                .list(elements)
                .perPage(paginatedList.getPerPage())
                .totalPages(paginatedList.getTotalPages())
                .nextPage(paginatedList.getNextPage())
                .previousPage(paginatedList.getPreviousPage())
                .page(paginatedList.getPage())
                .build();
    }

}
