package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

public abstract class TimeEntryCreationInfo {

    private Long userId;

    private Long projectId;

    private Long taskId;

    private LocalDate spentDate;

    private String notes;

    private Object externalReference;

    public TimeEntryCreationInfo(Reference<Project> projectReference, Reference<Task> taskReference,
            LocalDate spentDate) {
        projectId = projectReference.getId();
        taskId = taskReference.getId();
        this.spentDate = spentDate;
    }

    public void setUserReference(Reference<User> userReference) {
        userId = userReference.getId();
    }

    public void setProjectReference(Reference<Project> projectReference) {
        projectId = projectReference.getId();
    }

    public void setTaskReference(Reference<Task> taskReference) {
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
