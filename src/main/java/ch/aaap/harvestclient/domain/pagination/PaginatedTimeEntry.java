package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.TimeEntry;

public class PaginatedTimeEntry extends PaginatedList {

    private List<TimeEntry> timeEntries;

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @Override
    public String toString() {
        return "PaginatedTimeEntry{" +
                "timeEntries=" + timeEntries +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
