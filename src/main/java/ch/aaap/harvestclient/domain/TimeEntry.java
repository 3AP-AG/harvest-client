package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.*;

public class TimeEntry implements TimeEntryReference {

    private Long id;
    private LocalDate spentDate;
    private UserReferenceDto user;
    private UserAssignment userAssignment;
    private ClientReferenceDto client;
    private ProjectReferenceDto project;
    private TaskReferenceDto task;
    private TaskAssignment taskAssignment;

    @SerializedName("external_reference")
    private ExternalService externalService;
    private Invoice invoice;
    private Double hours;
    private String notes;

    @SerializedName("is_locked")
    private Boolean locked;
    private String lockedReason;

    @SerializedName("is_billed")
    private Boolean billed;
    private Instant timerStartedAt;
    private LocalTime startedTime;
    private LocalTime endedTime;

    @SerializedName("is_running")
    private Boolean running;

    @SerializedName("is_billable")
    private Boolean billable;
    private Boolean budgeted;
    private Double billableRate;
    private Double costRate;
    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(LocalDate spentDate) {
        this.spentDate = spentDate;
    }

    public UserReferenceDto getUser() {
        return user;
    }

    public void setUser(UserReferenceDto user) {
        this.user = user;
    }

    public UserAssignment getUserAssignment() {
        return userAssignment;
    }

    public void setUserAssignment(UserAssignment userAssignment) {
        this.userAssignment = userAssignment;
    }

    public ClientReferenceDto getClient() {
        return client;
    }

    public void setClient(ClientReferenceDto client) {
        this.client = client;
    }

    public ProjectReferenceDto getProject() {
        return project;
    }

    public void setProject(ProjectReferenceDto project) {
        this.project = project;
    }

    public TaskReferenceDto getTask() {
        return task;
    }

    public void setTask(TaskReferenceDto task) {
        this.task = task;
    }

    public TaskAssignment getTaskAssignment() {
        return taskAssignment;
    }

    public void setTaskAssignment(TaskAssignment taskAssignment) {
        this.taskAssignment = taskAssignment;
    }

    public ExternalService getExternalService() {
        return externalService;
    }

    public void setExternalService(ExternalService externalService) {
        this.externalService = externalService;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }

    public Boolean getBilled() {
        return billed;
    }

    public void setBilled(Boolean billed) {
        this.billed = billed;
    }

    public Instant getTimerStartedAt() {
        return timerStartedAt;
    }

    public void setTimerStartedAt(Instant timerStartedAt) {
        this.timerStartedAt = timerStartedAt;
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

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean getBudgeted() {
        return budgeted;
    }

    public void setBudgeted(Boolean budgeted) {
        this.budgeted = budgeted;
    }

    public Double getBillableRate() {
        return billableRate;
    }

    public void setBillableRate(Double billableRate) {
        this.billableRate = billableRate;
    }

    public Double getCostRate() {
        return costRate;
    }

    public void setCostRate(Double costRate) {
        this.costRate = costRate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TimeEntry{" + "id=" + id + ", spentDate=" + spentDate + ", user=" + user + ", userAssignment="
                + userAssignment + ", client=" + client + ", project=" + project + ", task=" + task
                + ", taskAssignment=" + taskAssignment + ", externalService=" + externalService + ", invoice=" + invoice
                + ", hours=" + hours + ", notes='" + notes + '\'' + ", locked=" + locked + ", lockedReason='"
                + lockedReason + '\'' + ", billed=" + billed + ", timerStartedAt=" + timerStartedAt + ", startedTime="
                + startedTime + ", endedTime=" + endedTime + ", running=" + running + ", billable=" + billable
                + ", budgeted=" + budgeted + ", billableRate=" + billableRate + ", costRate=" + costRate
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
