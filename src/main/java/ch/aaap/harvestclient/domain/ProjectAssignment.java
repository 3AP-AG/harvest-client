package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.ClientReference;
import ch.aaap.harvestclient.domain.reference.ProjectReference;

/**
 * User Project Assignment
 */
public class ProjectAssignment {

    private Long id;

    @SerializedName("is_active")
    private Boolean active;

    @SerializedName("is_project_manager")
    private Boolean projectManager;

    private Double hourlyRate;

    private Double budget;

    private Instant createdAt;

    private Instant updatedAt;

    private ProjectReference projectReference;

    private ClientReference clientReference;

    private List<TaskAssignment> taskAssignments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjectReference getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ProjectReference projectReference) {
        this.projectReference = projectReference;
    }

    public ClientReference getClientReference() {
        return clientReference;
    }

    public void setClientReference(ClientReference clientReference) {
        this.clientReference = clientReference;
    }

    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
        this.taskAssignments = taskAssignments;
    }

    @Override
    public String toString() {
        return "ProjectAssignment{" +
                "id=" + id +
                ", active=" + active +
                ", projectManager=" + projectManager +
                ", hourlyRate=" + hourlyRate +
                ", budget=" + budget +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", projectReference=" + projectReference +
                ", clientReference=" + clientReference +
                ", taskAssignments=" + taskAssignments +
                '}';
    }
}
