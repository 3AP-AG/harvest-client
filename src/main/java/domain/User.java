package domain;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;

public class User {

    private long id;
    private String firstName;
    private String LastName;
    private String email;
    private String telephone;
    private String timezone;
    private boolean hasAccessToAllFutureProjects;

    @SerializedName("is_contractor")
    private boolean contractor;
    @SerializedName("is_admin")
    private boolean admin;
    @SerializedName("is_project_manager")
    private boolean projectManager;
    private boolean canSeeRates;
    private boolean canCreateProjects;
    private boolean canCreateInvoices;

    @SerializedName("is_active")
    private boolean active;

    private long weeklyCapacity;
    private double defaultHourlyRate;
    private double CostRate;
    private List<String> roles;
    private String avatarUrl;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public String toString() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isHasAccessToAllFutureProjects() {
        return hasAccessToAllFutureProjects;
    }

    public void setHasAccessToAllFutureProjects(boolean hasAccessToAllFutureProjects) {
        this.hasAccessToAllFutureProjects = hasAccessToAllFutureProjects;
    }

    public boolean isContractor() {
        return contractor;
    }

    public void setContractor(boolean contractor) {
        this.contractor = contractor;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isProjectManager() {
        return projectManager;
    }

    public void setProjectManager(boolean projectManager) {
        this.projectManager = projectManager;
    }

    public boolean isCanSeeRates() {
        return canSeeRates;
    }

    public void setCanSeeRates(boolean canSeeRates) {
        this.canSeeRates = canSeeRates;
    }

    public boolean isCanCreateProjects() {
        return canCreateProjects;
    }

    public void setCanCreateProjects(boolean canCreateProjects) {
        this.canCreateProjects = canCreateProjects;
    }

    public boolean isCanCreateInvoices() {
        return canCreateInvoices;
    }

    public void setCanCreateInvoices(boolean canCreateInvoices) {
        this.canCreateInvoices = canCreateInvoices;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getWeeklyCapacity() {
        return weeklyCapacity;
    }

    public void setWeeklyCapacity(long weeklyCapacity) {
        this.weeklyCapacity = weeklyCapacity;
    }

    public double getDefaultHourlyRate() {
        return defaultHourlyRate;
    }

    public void setDefaultHourlyRate(double defaultHourlyRate) {
        this.defaultHourlyRate = defaultHourlyRate;
    }

    public double getCostRate() {
        return CostRate;
    }

    public void setCostRate(double costRate) {
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
