package ch.aaap.harvestclient.impl.timesheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.domain.TimeEntry;
import util.TestSetupUtil;

public class TimeSheetsTestConfig {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetsTestConfig.class);
    private static TimesheetsApi api = TestSetupUtil.getAdminAccess().timesheets();

    private static TimeEntry fixEntry;

    public static TimeEntry fixedEntry() {

        if (fixEntry == null) {
            // TODO create fix entries

            log.debug("Creating fixed Entry");

            TimeEntry entry = new TimeEntry();
            entry.setId(738720479L);
            fixEntry = api.get(entry);
        }

        return fixEntry;
    }
}
