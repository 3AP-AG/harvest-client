package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

public class TimeEntry {

    private long id;
    private LocalDate spentDate;
    private User user;
    private UserAssignment userAssignment;
    private Client client;
    private Project project;
    private Task task;
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

    public TimeEntry() {
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
