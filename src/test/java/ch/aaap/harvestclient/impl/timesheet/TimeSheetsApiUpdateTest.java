package ch.aaap.harvestclient.impl.timesheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
public class TimeSheetsApiUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetsApiUpdateTest.class);

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();
    private static TimeEntry fixEntry = ExistingData.getInstance().getTimeEntry();

    private static TimeEntry entry;

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {

        log.debug("Creating entry for test " + testInfo.getDisplayName());

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created for " + testInfo.getDisplayName();

        TimeEntryCreationInfoDuration creationInfo = new TimeEntryCreationInfoDuration(fixEntry.getProject(),
                fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setHours(1.);

        entry = api.create(creationInfo);
    }

    @AfterEach
    public void afterEach() {

        if (entry != null) {
            api.delete(entry);
        }
    }

    @Test
    public void testChangeDuration() {

        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setHours(3.);
        TimeEntry updatedEntry = api.update(entry, updateInfo);

        assertThat(updatedEntry.getHours()).isEqualTo(3.);
    }

    @Test
    public void testChangeNotes() {

        String newNotes = "This is an updated note";
        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setNotes(newNotes);
        TimeEntry updatedEntry = api.update(entry, updateInfo);

        assertThat(updatedEntry.getNotes()).isEqualTo(newNotes);
    }

    @Test
    @Disabled("needs second test account")
    public void testChangeStartedTime(TestInfo testInfo) {

        log.debug("Creating entry for test " + testInfo.getDisplayName());

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created for " + testInfo.getDisplayName();

        TimeEntryCreationInfoDuration creationInfo = new TimeEntryCreationInfoDuration(fixEntry.getProject(),
                fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setHours(1.);

        entry = api.create(creationInfo);

        assertThat(entry.getStartedTime()).isNotNull();

        LocalTime startedTime = LocalTime.of(13, 13);
        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setStartedTime(startedTime);

        TimeEntry updatedEntry = api.update(entry, updateInfo);

        assertThat(updatedEntry.getStartedTime()).isEqualTo(startedTime);
    }

    @Test
    public void testChangeAll() {

        String newNotes = "This is an updated note";
        double hours = 5.;
        LocalDate spentDate = LocalDate.of(2018, 2, 2);

        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setNotes(newNotes);
        updateInfo.setHours(hours);
        updateInfo.setSpentDate(spentDate);

        TimeEntry updatedEntry = api.update(entry, updateInfo);

        assertThat(updatedEntry.getNotes()).isEqualTo(newNotes);
        assertThat(updatedEntry.getHours()).isEqualTo(hours);
        assertThat(updatedEntry.getSpentDate()).isEqualTo(spentDate);

    }

    @Test
    public void testStopAndRestart() {

        assertThat(entry.getRunning()).isFalse();

        entry = api.restart(entry);

        assertThat(entry.getRunning()).isTrue();

        entry = api.stop(entry);

        assertThat(entry.getRunning()).isFalse();

    }

    /**
     * Convenience method. Delete all timeentries with spent_date is today or later.
     * Uncomment the Test annotation to run
     */
    // @Test
    public void deleteAllAfter() {

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setFrom(LocalDate.of(2018, 2, 1));

        List<TimeEntry> list = api.list(filter);

        list.forEach(api::delete);
        // no need to double delete
        entry = null;
    }
}
