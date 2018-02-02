package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.ExternalService;

public class TimeEntryUpdateInfo {

    private Long projectId;
    private Long taskId;
    private LocalDate spentDate;

    private LocalTime startedTime;
    private LocalTime endedTime;

    private Double hours;

    private String notes;

    @SerializedName("external_reference")
    private ExternalService externalService;

    @Override
    public String toString() {
        return "TimeEntryUpdateInfo{" +
                "projectId=" + projectId +
                ", taskId=" + taskId +
                ", spentDate=" + spentDate +
                ", startedTime=" + startedTime +
                ", endedTime=" + endedTime +
                ", hours=" + hours +
                ", notes='" + notes + '\'' +
                ", externalService=" + externalService +
                '}';
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDate getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(LocalDate spentDate) {
        this.spentDate = spentDate;
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ExternalService getExternalService() {
        return externalService;
    }

    public void setExternalService(ExternalService externalService) {
        this.externalService = externalService;
    }
}
