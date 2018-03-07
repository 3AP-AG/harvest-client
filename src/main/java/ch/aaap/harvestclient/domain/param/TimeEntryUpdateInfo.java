package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.ExternalService;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TimeEntryUpdateInfo {

    @Nullable
    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProjectReference();

    @Nullable
    @SerializedName(value = "task_id", alternate = "task")
    Reference<Task> getTaskReference();

    @Nullable
    LocalDate getSpentDate();

    @Nullable
    LocalTime getStartedTime();

    @Nullable
    LocalTime getEndedTime();

    @Nullable
    Double getHours();

    @Nullable
    String getNotes();

    @Nullable
    ExternalService getExternalReference();
}
