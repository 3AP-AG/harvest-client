package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

@Value.Immutable
public interface UserAssignment extends BaseObject<UserAssignment> {

    // only nullable inside of a TimeEntry
    @Nullable
    UserReferenceDto getUserReference();

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
