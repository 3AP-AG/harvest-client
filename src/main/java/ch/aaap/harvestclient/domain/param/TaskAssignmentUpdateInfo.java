package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TaskAssignmentUpdateInfo {

    @SerializedName("task")
    @Nullable
    Reference<Task> getTask();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

    @Nullable
    Boolean getBillable();

    @Nullable
    Double getHourlyRate();

    @Nullable
    Double getBudget();
}
