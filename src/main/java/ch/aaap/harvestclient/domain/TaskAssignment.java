package ch.aaap.harvestclient.domain;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class TaskAssignment implements Reference<TaskAssignment> {

    private Long id;

    @SerializedName("task")
    private TaskReferenceDto taskReferenceDto;

    @SerializedName("is_active")
    private Boolean active;

    private Boolean billable;

    private Double hourlyRate;
    private Double budget;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskReferenceDto getTaskReferenceDto() {
        return taskReferenceDto;
    }

    public void setTaskReferenceDto(TaskReferenceDto taskReferenceDto) {
        this.taskReferenceDto = taskReferenceDto;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
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
        return "TaskAssignment{" +
                "id=" + id +
                ", taskReferenceDto=" + taskReferenceDto +
                ", active=" + active +
                ", billable=" + billable +
                ", hourlyRate=" + hourlyRate +
                ", budget=" + budget +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
