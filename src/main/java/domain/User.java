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
    private List<Role> roles;
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

}
