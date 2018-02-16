package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.reference.Reference;

public class TimeEntryCreationInfoDuration extends TimeEntryCreationInfo {

    /**
     * The current amount of time tracked. Defaults to 0.0. If set, is_running will
     * be true, and false otherwise.
     */
    private Double hours;

    public TimeEntryCreationInfoDuration(Reference<Project> projectReference,
            Reference<Task> taskReference, LocalDate spentDate) {
        super(projectReference, taskReference, spentDate);
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
