package ch.aaap.harvestclient.domain;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

public class UserAssignment {

    private Long id;

    private UserReferenceDto userReference;

    @SerializedName("is_active")
    private Boolean active;

    @SerializedName("is_project_manager")
    private Boolean projectManager;

    private Double hourlyRate;

    private Double budget;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserReferenceDto getUserReference() {
        return userReference;
    }

    public void setUserReference(UserReferenceDto userReference) {
        this.userReference = userReference;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Boolean projectManager) {
        this.projectManager = projectManager;
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
        return "UserAssignment{" +
                "id=" + id +
                ", userReference=" + userReference +
                ", active=" + active +
                ", projectManager=" + projectManager +
                ", hourlyRate=" + hourlyRate +
                ", budget=" + budget +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
