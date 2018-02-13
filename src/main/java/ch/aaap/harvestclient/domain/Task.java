package ch.aaap.harvestclient.domain;

import java.time.Instant;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Value.Immutable
public interface Task extends Reference<Task> {

    @Override
    @Nullable
    Long getId();

    String getName();

    @Nullable
    Boolean getBillableByDefault();

    @Nullable
    Double getDefaultHourlyRate();

    @Nullable
    @SerializedName("is_default")
    Boolean getDefaultAddToFutureProjects();

    @Nullable
    @SerializedName("is_active")
    Boolean getActive();

    @Nullable
    Instant getCreatedAt();

    @Nullable
    Instant getUpdatedAt();

}
