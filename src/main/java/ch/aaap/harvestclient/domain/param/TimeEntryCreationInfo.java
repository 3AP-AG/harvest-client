package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import ch.aaap.harvestclient.domain.reference.ProjectReference;
import ch.aaap.harvestclient.domain.reference.TaskReference;
import ch.aaap.harvestclient.domain.reference.UserReference;

public abstract class TimeEntryCreationInfo {

    private Long userId;

    private Long projectId;

    private Long taskId;

    private LocalDate spentDate;

    private String notes;

    private Object externalReference;

    public TimeEntryCreationInfo(ProjectReference projectReference, TaskReference taskReference, LocalDate spentDate) {
        projectId = projectReference.getId();
        taskId = taskReference.getId();
        this.spentDate = spentDate;
    }

    public void setUserReference(UserReference userReference) {
        userId = userReference.getId();
    }

    public void setProjectReference(ProjectReference projectReference) {
        projectId = projectReference.getId();
    }

    public void setTaskReference(TaskReference taskReference) {
        taskId = taskReference.getId();
    }

    public LocalDate getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(LocalDate spentDate) {
        this.spentDate = spentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Object getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(Object externalReference) {
        this.externalReference = externalReference;
    }

}
