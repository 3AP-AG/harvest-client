package ch.aaap.harvestclient.impl.timesheet;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntry;
import util.TestSetupUtil;

public class TimeSheetsTestConfig {

    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static TimeEntry fixEntry;

    public static TimeEntry fixedEntry() {

        if (fixEntry == null) {
            // TODO create fix entries

            TimeEntry entry = new TimeEntry();
            entry.setId(738720479L);
            fixEntry = api.get(entry);
        }

        return fixEntry;
    }
}
