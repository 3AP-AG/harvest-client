package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.reference.Reference;

public class TimeEntryCreationInfoTimestamp extends TimeEntryCreationInfo {

    private static final Logger log = LoggerFactory.getLogger(TimeEntryCreationInfoTimestamp.class);

    /**
     * The time the entry started. Defaults to the current time.
     */
    private LocalTime startedTime;

    /**
     * The time the entry ended. If set, is_running will be false, and true
     * otherwise.
     */
    private LocalTime endedTime;

    public TimeEntryCreationInfoTimestamp(Reference<Project> projectReference,
            Reference<Task> taskReference, LocalDate spentDate) {
        super(projectReference, taskReference, spentDate);
    }

    public LocalTime getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(LocalTime startedTime) {
        this.startedTime = startedTime;
    }

    public LocalTime getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(LocalTime endedTime) {
        this.endedTime = endedTime;
    }

}
