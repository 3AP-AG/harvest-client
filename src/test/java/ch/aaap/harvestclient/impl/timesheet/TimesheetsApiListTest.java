package ch.aaap.harvestclient.impl.timesheet;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryListFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import util.TestSetupUtil;

@HarvestTest
class TimesheetsApiListTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static TimeEntry fixEntry = TestSetupUtil.getFixedTimeEntry();

    @Test
    public void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryListFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    public void testListFilterByUser() {

        TimeEntryListFilter filter = new TimeEntryListFilter();
        filter.setUserReference(fixEntry.getUser());

        List<TimeEntry> entries = api.list(filter);

        assertTrue(entries.size() > 0);

        assertTrue(entries.stream().map(TimeEntry::getId).anyMatch(id -> id.equals(fixEntry.getId())));
    }
}