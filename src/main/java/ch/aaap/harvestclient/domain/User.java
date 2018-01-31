package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.UserReference;

public class User implements UserReference {

    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private String telephone;
    private String timezone;
    private Boolean hasAccessToAllFutureProjects;

    @SerializedName("is_contractor")
    private Boolean contractor;

    @SerializedName("is_admin")

    private Boolean admin;
    @SerializedName("is_project_manager")
    private Boolean projectManager;
    private Boolean canSeeRates;
    private Boolean canCreateProjects;
    private Boolean canCreateInvoices;

    @SerializedName("is_active")
    private Boolean active;

    private Long weeklyCapacity;
    private Double defaultHourlyRate;
    private Double CostRate;
    private List<String> roles;
    private String avatarUrl;

    private Instant createdAt;
    private Instant updatedAt;

    public void setWeeklyCapacity(int amount, TimeUnit unit) {
        setWeeklyCapacity(unit.toSeconds(amount));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

    public String toStringFull() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", timezone='" + timezone + '\'' +
                ", hasAccessToAllFutureProjects=" + hasAccessToAllFutureProjects +
                ", contractor=" + contractor +
                ", admin=" + admin +
                ", projectManager=" + projectManager +
                ", canSeeRates=" + canSeeRates +
                ", canCreateProjects=" + canCreateProjects +
                ", canCreateInvoices=" + canCreateInvoices +
                ", active=" + active +
                ", weeklyCapacity=" + weeklyCapacity +
                ", defaultHourlyRate=" + defaultHourlyRate +
                ", CostRate=" + CostRate +
                ", roles=" + roles +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean getHasAccessToAllFutureProjects() {
        return hasAccessToAllFutureProjects;
    }

    public void setHasAccessToAllFutureProjects(Boolean hasAccessToAllFutureProjects) {
        this.hasAccessToAllFutureProjects = hasAccessToAllFutureProjects;
    }

    public Boolean getContractor() {
        return contractor;
    }

    public void setContractor(Boolean contractor) {
        this.contractor = contractor;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Boolean projectManager) {
        this.projectManager = projectManager;
    }

    public Boolean getCanSeeRates() {
        return canSeeRates;
    }

    public void setCanSeeRates(Boolean canSeeRates) {
        this.canSeeRates = canSeeRates;
    }

    public Boolean getCanCreateProjects() {
        return canCreateProjects;
    }

    public void setCanCreateProjects(Boolean canCreateProjects) {
        this.canCreateProjects = canCreateProjects;
    }

    public Boolean getCanCreateInvoices() {
        return canCreateInvoices;
    }

    public void setCanCreateInvoices(Boolean canCreateInvoices) {
        this.canCreateInvoices = canCreateInvoices;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getWeeklyCapacity() {
        return weeklyCapacity;
    }

    public void setWeeklyCapacity(Long weeklyCapacity) {
        this.weeklyCapacity = weeklyCapacity;
    }

    public Double getDefaultHourlyRate() {
        return defaultHourlyRate;
    }

    public void setDefaultHourlyRate(Double defaultHourlyRate) {
        this.defaultHourlyRate = defaultHourlyRate;
    }

    public Double getCostRate() {
        return CostRate;
    }

    public void setCostRate(Double costRate) {
        CostRate = costRate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
