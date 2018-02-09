package ch.aaap.harvestclient.domain.pagination;

import com.google.gson.annotations.SerializedName;

/**
 * Common functionality for paginated response. Cannot easily be made generic
 * because the Json key name for the list of objects depends on the type
 */
public class PaginatedList {

    Integer perPage;

    Integer totalPages;

    Integer nextPage;

    Integer previousPage;

    Integer page;

    @SerializedName("links")
    PaginationLinks paginationLinks;

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(Integer previousPage) {
        this.previousPage = previousPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public PaginationLinks getPaginationLinks() {
        return paginationLinks;
    }

    public void setPaginationLinks(PaginationLinks paginationLinks) {
        this.paginationLinks = paginationLinks;
    }

    @Override
    public String toString() {
        return "Paginated{" +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
