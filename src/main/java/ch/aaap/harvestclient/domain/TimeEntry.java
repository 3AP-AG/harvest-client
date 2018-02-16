package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

@Value.Immutable
public interface TimeEntry extends BaseObject<TimeEntry> {

    LocalDate getSpentDate();

    @Nullable
    UserReferenceDto getUser();

    @Nullable
    UserAssignment getUserAssignment();

    @Nullable
    ClientReferenceDto getClient();

    ProjectReferenceDto getProject();

    TaskReferenceDto getTask();

    @Nullable
    TaskAssignment getTaskAssignment();

    @SerializedName("external_reference")
    @Nullable
    ExternalService getExternalService();

    @Nullable
    Invoice getInvoice();

    @Nullable
    Double getHours();

    @Nullable
    String getNotes();

    @SerializedName("is_locked")
    @Nullable
    Boolean getLocked();

    @Nullable
    String getLockedReason();

    @SerializedName("is_billed")
    @Nullable
    Boolean getBilled();

    @Nullable
    Instant getTimerStartedAt();

    @Nullable
    LocalTime getStartedTime();

    @Nullable
    LocalTime getEndedTime();

    @SerializedName("is_running")
    @Nullable
    Boolean getRunning();

    @SerializedName("is_billable")
    @Nullable
    Boolean getBillable();

    @Nullable
    Boolean getBudgeted();

    @Nullable
    Double getBillableRate();

    @Nullable
    Double getCostRate();

}
