package domain;

import java.util.ArrayList;
import java.util.List;

public class TimeEntries {

    private List<TimeEntry> timeEntries = new ArrayList<>();

    public TimeEntries() {

    }

    public List<TimeEntry> getEntries() {
        return timeEntries;
    }
}
