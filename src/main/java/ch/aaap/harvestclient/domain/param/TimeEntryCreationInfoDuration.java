package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TimeEntryCreationInfoDuration extends TimeEntryCreationInfo {

    /**
     * The current amount of time tracked. Defaults to 0.0. If set, is_running will
     * be true, and false otherwise.
     */
    @Nullable
    Double getHours();

}
