package ch.aaap.harvestclient.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntries;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.service.TimeEntryService;
import retrofit2.Call;
import retrofit2.Response;

public class TimesheetsApiImpl implements TimesheetsApi {
    private final static Logger log = LoggerFactory.getLogger(TimesheetsApiImpl.class);
    private final TimeEntryService service;

    public TimesheetsApiImpl(TimeEntryService timeEntryService) {
        service = timeEntryService;
    }

    // TODO error handling
    @Override
    public List<TimeEntry> list() {
        Call<TimeEntries> call = service.listAll();
        try {
            Response<TimeEntries> response = call.execute();
            List<TimeEntry> timeEntries = response.body().getEntries();
            log.debug("Listed {} TimeEntries: {}", timeEntries.size(), timeEntries);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public TimeEntry get(long timeEntryId) {
        return null;
    }

    @Override
    public void create(long projectId, long taskId, LocalDate spentDate) {

    }

    @Override
    public void create(long projectId, long taskId, LocalDate spentDate, long userId) {

    }

    @Override
    public void delete(long timeEntryId) {

    }
}
