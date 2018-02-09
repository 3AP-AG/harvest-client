package ch.aaap.harvestclient.impl.timesheet;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TimesheetsApiListTest {

    private static final TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static final TimeEntry fixEntry = ExistingData.getInstance().getTimeEntry();

    @Test
    public void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    public void testListFilterByUser() {

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setUserReference(fixEntry.getUser());

        List<TimeEntry> entries = api.list(filter);

        assertTrue(entries.size() > 0);

        assertTrue(entries.stream().map(TimeEntry::getId).anyMatch(id -> id.equals(fixEntry.getId())));
    }
}