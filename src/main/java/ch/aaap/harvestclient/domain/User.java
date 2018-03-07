package ch.aaap.harvestclient.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public abstract class User implements BaseObject<User> {

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    @Nullable
    public abstract String getTelephone();

    @Nullable
    public abstract String getTimezone();

    @Nullable
    public abstract Boolean getHasAccessToAllFutureProjects();

    @SerializedName("is_contractor")
    @Nullable
    public abstract Boolean getContractor();

    @SerializedName("is_admin")
    @Nullable
    public abstract Boolean getAdmin();

    @SerializedName("is_project_manager")
    @Nullable
    public abstract Boolean getProjectManager();

    @Nullable
    public abstract Boolean getCanSeeRates();

    @Nullable
    public abstract Boolean getCanCreateProjects();

    @Nullable
    public abstract Boolean getCanCreateInvoices();

    @SerializedName("is_active")
    @Nullable
    public abstract Boolean getActive();

    @Nullable
    public abstract Long getWeeklyCapacity();

    @Nullable
    public abstract Double getDefaultHourlyRate();

    @Nullable
    public abstract Double getCostRate();

    @Nullable
    @Value.Default
    public List<String> getRoles() {
        return new ArrayList<>();
    }

    @Nullable
    public abstract String getAvatarUrl();

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
