package ch.aaap.harvestclient.domain;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * User Project Assignment
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ProjectAssignment extends BaseObject<ProjectAssignment> {

    @SerializedName("is_active")
    Boolean getActive();

    @SerializedName("is_project_manager")
    Boolean getProjectManager();

    @Nullable
    Double getHourlyRate();

    @Nullable
    Double getBudget();

    @SerializedName(value = "project", alternate = "project_id")
    Reference<Project> getProject();

    @SerializedName(value = "client", alternate = "client_id")
    Reference<Client> getClient();

    List<TaskAssignment> getTaskAssignments();
}
