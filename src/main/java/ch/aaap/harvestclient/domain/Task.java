package ch.aaap.harvestclient.domain;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

public class Task implements Reference<Task> {

    private Long id;
    private String name;
    private Boolean billableByDefault;
    private Double defaultHourlyRate;

    @SerializedName("is_default")
    private Boolean defaultAddToFutureProjects;

    @SerializedName("is_active")
    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBillableByDefault() {
        return billableByDefault;
    }

    public void setBillableByDefault(Boolean billableByDefault) {
        this.billableByDefault = billableByDefault;
    }

    public Double getDefaultHourlyRate() {
        return defaultHourlyRate;
    }

    public void setDefaultHourlyRate(Double defaultHourlyRate) {
        this.defaultHourlyRate = defaultHourlyRate;
    }

    public Boolean getDefaultAddToFutureProjects() {
        return defaultAddToFutureProjects;
    }

    public void setDefaultAddToFutureProjects(Boolean defaultAddToFutureProjects) {
        this.defaultAddToFutureProjects = defaultAddToFutureProjects;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", billableByDefault=" + billableByDefault +
                ", defaultHourlyRate=" + defaultHourlyRate +
                ", defaultAddToFutureProjects=" + defaultAddToFutureProjects +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
