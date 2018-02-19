package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoTimestamp;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.TimeEntryService;
import retrofit2.Call;

public class TimesheetsApiImpl implements TimesheetsApi {
    private final static Logger log = LoggerFactory.getLogger(TimesheetsApiImpl.class);
    private final TimeEntryService service;

    public TimesheetsApiImpl(TimeEntryService timeEntryService) {
        service = timeEntryService;
    }

    @Override
    public List<TimeEntry> list(TimeEntryFilter filter) {
        return Common.collect((page, perPage) -> list(filter, page, perPage));
    }

    @Override
    public Pagination<TimeEntry> list(TimeEntryFilter filter, int page, int perPage) {
        log.debug("Getting page {} of timeentries list", page);

        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));

        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getTimeEntries());
    }

    @Override
    public TimeEntry get(Reference<TimeEntry> timeEntryReference) {
        Call<TimeEntry> call = service.get(timeEntryReference.getId());
        TimeEntry entry = ExceptionHandler.callOrThrow(call);

        log.debug("Got entry {}", entry);
        return entry;
    }

    @Override
    public TimeEntry create(TimeEntryCreationInfoDuration creationInfo) {
        Call<TimeEntry> call = service.create(creationInfo);
        TimeEntry timeEntry = ExceptionHandler.callOrThrow(call);

        log.debug("Created {} ", timeEntry);

        return timeEntry;
    }

    @Override
    public TimeEntry create(TimeEntryCreationInfoTimestamp creationInfo) {

        Call<TimeEntry> call = service.create(creationInfo);
        TimeEntry timeEntry = ExceptionHandler.callOrThrow(call);

        log.debug("Created {} ", timeEntry);

        return timeEntry;

    }

    @Override
    public TimeEntry update(Reference<TimeEntry> timeEntryReference, TimeEntryUpdateInfo updatedInfo) {

        log.debug("Updating properties {} for timeentry {}", updatedInfo, timeEntryReference);
        Call<TimeEntry> call = service.update(timeEntryReference.getId(), updatedInfo);
        return ExceptionHandler.callOrThrow(call);

    }

    @Override
    public void delete(Reference<TimeEntry> timeEntryReference) {
        log.debug("Deleting TimeEntry {}", timeEntryReference);

        Call<Void> call = service.delete(timeEntryReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

    @Override
    public TimeEntry restart(Reference<TimeEntry> timeEntryReference) {

        Call<TimeEntry> call = service.restart(timeEntryReference.getId());
        TimeEntry entry = ExceptionHandler.callOrThrow(call);

        log.debug("Restarted entry {}", entry);

        return entry;
    }

    @Override
    public TimeEntry stop(Reference<TimeEntry> timeEntryReference) {
        Call<TimeEntry> call = service.stop(timeEntryReference.getId());
        TimeEntry entry = ExceptionHandler.callOrThrow(call);

        log.debug("Stopped entry {}", entry);

        return entry;
    }
}
