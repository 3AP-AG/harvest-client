package ch.aaap.harvestclient.impl;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryListFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import util.TestSetupUtil;

class TimesheetsApiImplTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static TimeEntry fixEntry;

    @BeforeAll
    public static void beforeAll() {

        // TODO create fix entries

        TimeEntry entry = new TimeEntry();
        entry.setId(738720479L);
        fixEntry = api.get(entry);
    }

    @Test
    public void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryListFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    public void testCreateTimeEntry() {

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created by automated Test";

        TimeEntryCreationInfo creationInfo = new TimeEntryCreationInfo(fixEntry.getProject(), fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setHours(2.);

        TimeEntry timeEntry = api.create(creationInfo);

        assertEquals(fixEntry.getProject(), timeEntry.getProject());
        assertEquals(notes, timeEntry.getNotes());

        // cleanup
        api.delete(timeEntry);
    }

    @Test
    public void testListFilterByUser() {

        TimeEntryListFilter filter = new TimeEntryListFilter();
        filter.setUserReference(fixEntry.getUser());

        List<TimeEntry> entries = api.list(filter);

        assertTrue(entries.size() > 0);

        assertTrue(entries.stream().map(TimeEntry::getId).anyMatch(id -> id.equals(fixEntry.getId())));

    }

    public TimeEntry createStandardEntry() {

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created by automated Test";

        TimeEntryCreationInfo creationInfo = new TimeEntryCreationInfo(fixEntry.getProject(), fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setHours(1.);

        return api.create(creationInfo);
    }

    @Test
    public void testChangeDuration() {

        TimeEntry entry = createStandardEntry();

        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setHours(3.);
        TimeEntry updatedEntry = api.update(entry, updateInfo);

        assertEquals(3., (double) updatedEntry.getHours());

        api.delete(updatedEntry);
    }

    @Test
    public void testRestart() {

    }

    @Test
    public void testStop() {

    }
}