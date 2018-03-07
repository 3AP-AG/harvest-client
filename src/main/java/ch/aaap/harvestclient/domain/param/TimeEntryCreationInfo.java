package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.ExternalService;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface TimeEntryCreationInfo {

    @Nullable
    @SerializedName(value = "user_id", alternate = "user")
    Reference<User> getUserReference();

    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProjectReference();

    @SerializedName(value = "task_id", alternate = "task")
    Reference<Task> getTaskReference();

    LocalDate getSpentDate();

    @Nullable
    String getNotes();

    @Nullable
    ExternalService getExternalReference();

}
