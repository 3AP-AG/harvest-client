package ch.aaap.harvestclient.impl.timesheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoTimestamp;
import util.TestSetupUtil;

@HarvestTest
public class TimeSheetsApiCreateTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    /**
     * Use this to get the Project, Task and User
     */
    private static TimeEntry fixEntry = TestSetupUtil.getFixedTimeEntry();

    private static TimeEntry entry;

    // TODO this test relies on the local timezone to be the same as the harvest one
    // fix by reading the harvest timezone and adjusting the test
    private static final ZoneId companyTimeZone = ZoneId.of("Europe/Berlin");

    @AfterEach
    public void afterEach() {
        if (entry != null) {
            api.delete(entry);
        }
    }

    /**
     * Needs company.wantsTimestampTimers set to false
     */
    @Test
    public void testCreateTimeEntryDuration(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();

        TimeEntryCreationInfoDuration creationInfo = new TimeEntryCreationInfoDuration(fixEntry.getProject(),
                fixEntry.getTask(), date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setHours(2.);

        TimeEntry timeEntry = api.create(creationInfo);

        assertEquals(fixEntry.getProject(), timeEntry.getProject());
        assertEquals(notes, timeEntry.getNotes());
        assertThat(timeEntry.getRunning()).isFalse();
        assertThat(timeEntry.getHours()).isEqualTo(2.);

        // if hours is set there is no timer
        assertThat(timeEntry.getTimerStartedAt()).isNull();

    }

    /**
     * Needs company.wantsTimestampTimers set to true
     */
    @Test
    @Disabled("needs second test account")
    public void testCreateTimeEntryWithStartAndEndTime(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();
        // harvest does not store seconds in started_time
        LocalTime startedTime = LocalTime.now(companyTimeZone).truncatedTo(ChronoUnit.MINUTES);

        TimeEntryCreationInfoTimestamp creationInfo = new TimeEntryCreationInfoTimestamp(fixEntry.getProject(),
                fixEntry.getTask(), date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setStartedTime(startedTime);
        creationInfo.setEndedTime(startedTime.plusHours(3));

        TimeEntry timeEntry = api.create(creationInfo);

        assertThat(timeEntry.getStartedTime()).isEqualTo(startedTime);
        assertThat(timeEntry.getEndedTime()).isEqualTo(startedTime.plusHours(3));

        // timer was not started since endtime was given
        assertThat(timeEntry.getTimerStartedAt()).isNull();

        assertThat(timeEntry.getRunning()).isFalse();
    }

    @Test
    public void testCreateTimeEntryDefault(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();

        TimeEntryCreationInfoDuration creationInfo = new TimeEntryCreationInfoDuration(fixEntry.getProject(),
                fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        // not setting anything specific

        TimeEntry timeEntry = api.create(creationInfo);

        assertThat(timeEntry.getTimerStartedAt()).isNotNull();

        ZonedDateTime timerStartedAt = timeEntry.getTimerStartedAt().atZone(companyTimeZone);

        assertThat(timeEntry.getStartedTime()).isEqualToIgnoringSeconds(timerStartedAt.toLocalTime());
        assertThat(timeEntry.getRunning()).isTrue();
        assertThat(timeEntry.getHours()).isEqualTo(0.);
    }

    /**
     * Should behave exactly like {@link #testCreateTimeEntryDefault(TestInfo)}
     * 
     * @param testInfo
     */
    @Test
    public void testCreateTimeEntryTimestampDefault(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();

        TimeEntryCreationInfoTimestamp creationInfo = new TimeEntryCreationInfoTimestamp(fixEntry.getProject(),
                fixEntry.getTask(),
                date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        // not setting anything specific

        TimeEntry timeEntry = api.create(creationInfo);

        assertThat(timeEntry.getTimerStartedAt()).isNotNull();

        ZonedDateTime timerStartedAt = timeEntry.getTimerStartedAt().atZone(companyTimeZone);

        assertThat(timeEntry.getStartedTime()).isEqualToIgnoringSeconds(timerStartedAt.toLocalTime());
        assertThat(timeEntry.getRunning()).isTrue();
        assertThat(timeEntry.getHours()).isEqualTo(0.);
    }

    @Test
    public void testCreateTimeEntryWithStartTime(TestInfo testInfo) {

        LocalDate date = LocalDate.now();
        String notes = "TimeEntry created by " + testInfo.getDisplayName();
        // harvest does not store seconds in started_time
        LocalTime startedTime = LocalTime.now(companyTimeZone).truncatedTo(ChronoUnit.MINUTES);

        TimeEntryCreationInfoTimestamp creationInfo = new TimeEntryCreationInfoTimestamp(fixEntry.getProject(),
                fixEntry.getTask(), date);
        creationInfo.setNotes(notes);
        creationInfo.setUserReference(fixEntry.getUser());
        creationInfo.setStartedTime(startedTime);

        TimeEntry timeEntry = api.create(creationInfo);

        assertEquals(fixEntry.getProject(), timeEntry.getProject());
        assertEquals(notes, timeEntry.getNotes());

        assertThat(timeEntry.getStartedTime()).isEqualTo(startedTime);

        // check timer_started_at is in sync at least up to hour and minute
        ZonedDateTime zonedDateTime = timeEntry.getTimerStartedAt().atZone(companyTimeZone);
        assertThat(timeEntry.getStartedTime()).isEqualToIgnoringSeconds(zonedDateTime.toLocalTime());

        assertThat(timeEntry.getRunning()).isTrue();
    }

}
