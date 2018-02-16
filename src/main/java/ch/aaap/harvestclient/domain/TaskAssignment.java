package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Value.Immutable
public interface TaskAssignment extends BaseObject<TaskAssignment> {

    @SerializedName("task")
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
