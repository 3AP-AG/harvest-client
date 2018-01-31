package ch.aaap.harvestclient.api;

import java.time.LocalDate;
import java.util.List;

import ch.aaap.harvestclient.api.filter.TimeEntryListFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;

public interface TimesheetsApi {

    /**
     * Return a list of all TimeEntries, filtered by the TimeEntryListFilter, sorted
     * by creation date, newest first.
     * 
     * @return a list of all matching TimeEntry, newest first.
     */
    List<TimeEntry> list(TimeEntryListFilter filter);

    TimeEntry get(long timeEntryId);

    TimeEntry create(TimeEntryCreationInfo creationInfo);

    void create(long projectId, long taskId, LocalDate spentDate, long userId);

    void delete(long timeEntryId);
}
