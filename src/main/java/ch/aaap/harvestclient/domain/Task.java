package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Task extends BaseObject<Task> {

    /**
     * Max 255 characters
     * 
     * @return the current value
     */
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

}
