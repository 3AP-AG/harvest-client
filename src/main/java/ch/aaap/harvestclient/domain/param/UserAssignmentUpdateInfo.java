package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.BaseObject;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface UserAssignmentUpdateInfo extends BaseObject<UserAssignmentUpdateInfo> {

    @SerializedName(value = "user_id", alternate = "user")
    @Nullable
    Reference<User> getUser();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

    @SerializedName("is_project_manager")
    @Nullable
    Boolean getProjectManager();

    @Nullable
    Double getHourlyRate();

    @Nullable
    Double getBudget();

}
