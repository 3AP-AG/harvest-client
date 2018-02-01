package ch.aaap.harvestclient.impl.timesheet;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import util.TestSetupUtil;

public class TimeSheetsApiCreateTest {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static TimeEntry fixEntry = TimeSheetsTestConfig.fixedEntry();

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
}
