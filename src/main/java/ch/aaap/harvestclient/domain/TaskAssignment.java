package ch.aaap.harvestclient.domain;

import java.time.Instant;

import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class TaskAssignment {

    private Long id;
    private TaskReferenceDto taskReferenceDto;
    private Boolean active;
    private Boolean billable;
    private Boolean hourlyRate;
    private Boolean budget;

    private Instant createdAt;
    private Instant updatedAt;

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

    public Boolean getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Boolean hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean getBudget() {
        return budget;
    }

    public void setBudget(Boolean budget) {
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
