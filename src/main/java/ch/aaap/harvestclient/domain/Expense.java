package ch.aaap.harvestclient.domain;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Expense extends BaseObject<Expense> {

    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    ProjectReferenceDto getProjectReferenceDto();

    ExpenseCategory getExpenseCategory();

    @Nullable
    UserReferenceDto getUserReferenceDto();

    @Nullable
    UserAssignment getUserAssignment();

    @Nullable
    Receipt getReceipt();

    @Nullable
    Invoice getInvoice();

    @Nullable
    String getNotes();

    @Nullable
    Boolean getBillable();

    @Nullable
    Boolean getClosed();

    @Nullable
    Boolean getLocked();

    @Nullable
    Boolean getBilled();

    @Nullable
    String getLockedReason();

    LocalDate getSpentDate();

}
