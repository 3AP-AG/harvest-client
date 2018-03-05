package ch.aaap.harvestclient.impl.timesheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.core.TimezoneConfiguration;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoTimestamp;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TimeSheetsApiWithStartTimeTest {

    private static final Harvest harvest = TestSetupUtil.getAnotherAdminAccess();
    private static final TimesheetsApi api = harvest.timesheets();

    private static final Reference<Project> project = ExistingData.getAnotherInstance().getProjectReference();
    private static final Reference<Task> task = ExistingData.getAnotherInstance().getTaskReference();
    private static final Reference<User> user = ExistingData.getAnotherInstance().getUserReference();

    private static TimeEntry timeEntry;

    private static ZoneId userTimeZone;

    @BeforeAll
    static void beforeAll() {
        User tempUser = harvest.users().get(user);
        TimezoneConfiguration timezoneConfiguration = harvest.getTimezoneConfiguration();
        userTimeZone = timezoneConfiguration.getZoneId(tempUser.getTimezone()).get();
    }

    @AfterEach
    void afterEach() {
        if (timeEntry != null) {
            api.delete(timeEntry);
        }
    }

    /*
     * Needs company.wantsTimestampTimers set to true
     */
    @Test
    void testCreateTimeEntryWithStartAndEndTime(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();
        // harvest does not store seconds in started_time
        LocalTime startedTime = LocalTime.now(userTimeZone).truncatedTo(ChronoUnit.MINUTES);

        TimeEntryCreationInfoTimestamp creationInfo = new TimeEntryCreationInfoTimestamp(project,
                task, date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(user);
        creationInfo.setStartedTime(startedTime);
        creationInfo.setEndedTime(startedTime.plusHours(3));

        timeEntry = api.create(creationInfo);

        assertThat(timeEntry.getStartedTime()).isEqualTo(startedTime);
        assertThat(timeEntry.getEndedTime()).isEqualTo(startedTime.plusHours(3));

        // timer was not started since endtime was given
        assertThat(timeEntry.getTimerStartedAt()).isNull();

        assertThat(timeEntry.getRunning()).isFalse();
    }

    @Test
    void testCreateTimeEntryWithStartTime(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();
        // harvest does not store seconds in started_time
        LocalTime startedTime = LocalTime.now(userTimeZone).truncatedTo(ChronoUnit.MINUTES);

        TimeEntryCreationInfoTimestamp creationInfo = new TimeEntryCreationInfoTimestamp(project,
                task, date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(user);
        creationInfo.setStartedTime(startedTime);

        timeEntry = api.create(creationInfo);

        assertEquals(project.getId(), timeEntry.getProject().getId());
        assertEquals(notes, timeEntry.getNotes());

        assertThat(timeEntry.getStartedTime()).isEqualTo(startedTime);

        // check timer_started_at is in sync at least up to hour and minute
        ZonedDateTime zonedDateTime = timeEntry.getTimerStartedAt().atZone(userTimeZone);
        assertThat(timeEntry.getStartedTime()).isEqualToIgnoringSeconds(zonedDateTime.toLocalTime());

        assertThat(timeEntry.getRunning()).isTrue();
    }

    @Test
    void testChangeStartedTime(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created for " + testInfo.getDisplayName();

        TimeEntryCreationInfoDuration creationInfo = new TimeEntryCreationInfoDuration(project, task, date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(user);
        creationInfo.setHours(1.);

        timeEntry = api.create(creationInfo);

        assertThat(timeEntry.getStartedTime()).isNotNull();

        LocalTime startedTime = LocalTime.of(13, 13);
        TimeEntryUpdateInfo updateInfo = new TimeEntryUpdateInfo();
        updateInfo.setStartedTime(startedTime);

        TimeEntry updatedEntry = api.update(timeEntry, updateInfo);

        assertThat(updatedEntry.getStartedTime()).isEqualTo(startedTime);
    }

}
