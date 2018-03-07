package ch.aaap.harvestclient.domain.param;

import java.time.LocalTime;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TimeEntryCreationInfoTimestamp extends TimeEntryCreationInfo {

    /**
     * The time the entry started. Defaults to the current time.
     */
    @Nullable
    LocalTime getStartedTime();

    /**
     * The time the entry ended. If set, is_running will be false, and true
     * otherwise.
     */
    @Nullable
    LocalTime getEndedTime();
}
