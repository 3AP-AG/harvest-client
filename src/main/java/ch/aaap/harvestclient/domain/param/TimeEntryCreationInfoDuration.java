package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.domain.reference.ProjectReference;
import ch.aaap.harvestclient.domain.reference.TaskReference;

public class TimeEntryCreationInfoDuration extends TimeEntryCreationInfo {

    private static final Logger log = LoggerFactory.getLogger(TimeEntryCreationInfoDuration.class);

    /**
     * The current amount of time tracked. Defaults to 0.0. If set, is_running will
     * be true, and false otherwise.
     */
    private Double hours;

    public TimeEntryCreationInfoDuration(ProjectReference projectReference,
            TaskReference taskReference, LocalDate spentDate) {
        super(projectReference, taskReference, spentDate);
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
