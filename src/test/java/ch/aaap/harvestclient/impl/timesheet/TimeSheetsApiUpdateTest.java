package ch.aaap.harvestclient.impl.timesheet;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import util.TestSetupUtil;

public class TimeSheetsApiUpdateTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();
    private static TimeEntry fixEntry = TimeSheetsTestConfig.fixedEntry();

    private static TimeEntry entry;

    @BeforeEach
    public void beforeEach() {

        LocalDate date = LocalDate.now();
        String notes = "Timeentry created by automated Test";

        TimeEntryCreationInfo creationInfo = new TimeEntryCreationInfo(fixEntry.getProject(), fixEntry.getTask(),
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
    public void testRestart() {

    }

    @Test
    public void testStop() {

    }
}
