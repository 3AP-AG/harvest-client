package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TimeEntryListFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.TimeEntryReference;

public interface TimesheetsApi {

    /**
     * Return a list of all TimeEntries, filtered by the TimeEntryListFilter, sorted
     * by creation date, newest first.
     * 
     * @return a list of all matching TimeEntry, newest first.
     */
    List<TimeEntry> list(TimeEntryListFilter filter);

    /**
     * Retrieve an existing TimeEntry
     * 
     * @param timeEntryReference
     *            a reference to an the timeentry to retrieve
     * @return a Full TimeEntry object
     */
    TimeEntry get(TimeEntryReference timeEntryReference);

    /**
     * 
     * @param creationInfo
     * @return
     */
    TimeEntry create(TimeEntryCreationInfo creationInfo);

    /**
     * Modify an existing TimeEntry.
     * 
     * @param timeEntryReference
     *            the existing TimeEntry to be modified
     * @param updatedInfo
     *            the changes to be done
     * @return the updated TimeEntry
     * @see #stop(TimeEntryReference)
     * @see #restart(TimeEntryReference)
     */
    TimeEntry update(TimeEntryReference timeEntryReference, TimeEntryUpdateInfo updatedInfo);

    /**
     * Delete an existing TimeEntry
     * 
     * @param timeEntryReference
     *            a reference to the TimeEntry to be deleted
     */
    void delete(TimeEntryReference timeEntryReference);

    /**
     * Restart a TimeEntry. Only possible if it is not running
     * 
     * @param timeEntryReference
     *            a reference to the TimeEntry to be restarted
     * @return the modified TimeEntry
     */
    TimeEntry restart(TimeEntryReference timeEntryReference);

    /**
     * Stop a TimeEntry. Only possible if it is running
     *
     * @param timeEntryReference
     *            a reference to the TimeEntry to be stopped
     * @return the modified TimeEntry
     */
    TimeEntry stop(TimeEntryReference timeEntryReference);
}
