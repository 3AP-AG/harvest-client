package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.ClientReferenceDto;
import ch.aaap.harvestclient.domain.reference.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.TaskReferenceDto;
import ch.aaap.harvestclient.domain.reference.UserReferenceDto;

public class TimeEntry {

    private long id;
    private LocalDate spentDate;
    private UserReferenceDto user;
    private UserAssignment userAssignment;
    private ClientReferenceDto client;
    private ProjectReferenceDto project;
    private TaskReferenceDto task;
    private TaskAssignment taskAssignment;
    private ExternalService externalService;
    private Invoice invoice;
    private double hours;
    private String notes;

    @SerializedName("is_locked")
    private boolean locked;
    private String lockedReason;

    @SerializedName("is_billed")
    private boolean billed;
    private Instant timerStartedAt;
    private LocalTime startedTime;
    private LocalTime endedTime;

    @SerializedName("is_running")
    private boolean Running;

    @SerializedName("is_billable")
    private boolean billable;
    private boolean budgeted;
    private double billableRate;
    private double costRate;
    private Instant createdAt;
    private Instant updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
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

    public boolean isRunning() {
        return Running;
    }

    public void setRunning(boolean running) {
        Running = running;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public boolean isBudgeted() {
        return budgeted;
    }

    public void setBudgeted(boolean budgeted) {
        this.budgeted = budgeted;
    }

    public double getBillableRate() {
        return billableRate;
    }

    public void setBillableRate(double billableRate) {
        this.billableRate = billableRate;
    }

    public double getCostRate() {
        return costRate;
    }

    public void setCostRate(double costRate) {
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
                + startedTime + ", endedTime=" + endedTime + ", Running=" + Running + ", billable=" + billable
                + ", budgeted=" + budgeted + ", billableRate=" + billableRate + ", costRate=" + costRate
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
