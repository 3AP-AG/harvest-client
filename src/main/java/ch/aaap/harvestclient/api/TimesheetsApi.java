package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoTimestamp;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(value = Api.Role.NONE, onlySelf = true)
public interface TimesheetsApi extends Api.Get<TimeEntry> {

    /**
     * Return a list of all TimeEntries, filtered by the TimeEntryFilter, sorted by
     * creation date, newest first.
     *
     * @param filter
     *            filtering options
     * @return a list of all matching TimeEntry, newest first.
     */
    List<TimeEntry> list(TimeEntryFilter filter);

    /**
     * Return a list of all TimeEntries, filtered by the TimeEntryFilter, sorted by
     * creation date, newest first. Page and perPage allow controlling how many
     * results to return.
     * 
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all matching TimeEntry, newest first.
     */
    Pagination<TimeEntry> list(TimeEntryFilter filter, int page, int perPage);

    /**
     * Retrieve an existing TimeEntry
     * 
     * @param timeEntryReference
     *            a reference to an the timeentry to retrieve
     * @return a Full TimeEntry object
     */
    @Override
    TimeEntry get(Reference<TimeEntry> timeEntryReference);

    /**
     * Create a TimeEntry with a duration. If no duration is given, the TimeEntry
     * will be running, otherwise it will not be.
     *
     * @param creationInfo
     *            creation options
     * @return the created TimeEntry
     * @see TimeEntryCreationInfoDuration
     */
    TimeEntry create(TimeEntryCreationInfoDuration creationInfo);

    /**
     * Create a TimeEntry with a started time. The Entry will be running after if a
     * startTime has been given.
     * 
     * @param creationInfo
     *            creation options
     * @return the created TimeEntry
     * @see TimeEntryCreationInfoTimestamp
     */
    TimeEntry create(TimeEntryCreationInfoTimestamp creationInfo);

    /**
     * Modify an existing TimeEntry.
     * 
     * @param timeEntryReference
     *            the existing TimeEntry to be modified
     * @param updatedInfo
     *            the changes to be done
     * @return the updated TimeEntry
     * @see #stop(Reference)
     * @see #restart(Reference)
     */
    TimeEntry update(Reference<TimeEntry> timeEntryReference, TimeEntryUpdateInfo updatedInfo);

    /**
     * Delete an existing TimeEntry
     * 
     * @param timeEntryReference
     *            a reference to the TimeEntry to be deleted
     */
    void delete(Reference<TimeEntry> timeEntryReference);

    /**
     * Restart a TimeEntry. Only possible if it is not running
     * 
     * @param timeEntryReference
     *            a reference to the TimeEntry to be restarted
     * @return the modified TimeEntry
     */
    TimeEntry restart(Reference<TimeEntry> timeEntryReference);

    /**
     * Stop a TimeEntry. Only possible if it is running
     *
     * @param timeEntryReference
     *            a reference to the TimeEntry to be stopped
     * @return the modified TimeEntry
     */
    TimeEntry stop(Reference<TimeEntry> timeEntryReference);
}
