package ch.aaap.harvestclient.impl;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryListFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import util.TestSetupUtil;

class TimesheetsApiImplTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    @Test
    public void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryListFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    public void testCreateTimeEntry() {

        List<TimeEntry> timeEntries = api.list(TimeEntryListFilter.emptyFilter());

        TimeEntry first = timeEntries.get(0);

        LocalDate date = LocalDate.now();
        String notes = "Test notes";

        TimeEntryCreationInfo creationInfo = new TimeEntryCreationInfo(first.getProject(), first.getTask(),
                date);

        creationInfo.setNotes(notes);

        TimeEntry timeEntry = api.create(creationInfo);

        assertEquals(first.getProject(), timeEntry.getProject());
        assertEquals(notes, timeEntry.getNotes());

    }
}