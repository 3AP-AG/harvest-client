package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TaskAssignment extends BaseObject<TaskAssignment> {

    @SerializedName(value = "task_id", alternate = "task")
    // nullable in the Project list
    @Nullable
    Reference<Task> getTaskReference();

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
