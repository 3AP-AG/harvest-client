package ch.aaap.harvestclient.impl.timesheet;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TimesheetsApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final TimesheetsApi api = harvest.timesheets();

    private static final Reference<TimeEntry> fixEntryReference = ExistingData.getInstance().getTimeEntryReference();
    private static final Reference<User> user = ExistingData.getInstance().getUserReference();

    @Test
    void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    void testListFilterByUser() {

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setUserReference(harvest.users().getSelf());

        // TODO timeentry should be for the ExistingData user
        List<TimeEntry> entries = api.list(filter);

        assertTrue(entries.size() > 0);

        assertTrue(entries.stream().map(TimeEntry::getId).anyMatch(id -> id.equals(fixEntryReference.getId())));
    }

    @Test
    void listPaginated() {

        Pagination<TimeEntry> pagination = api.list(new TimeEntryFilter(), 1, 1);

        List<TimeEntry> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }
}