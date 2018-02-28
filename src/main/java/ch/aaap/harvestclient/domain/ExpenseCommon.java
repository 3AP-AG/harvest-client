package ch.aaap.harvestclient.domain;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

public interface ExpenseCommon {

    @Nullable
    Double getTotalCost();

    @Nullable
    Double getUnits();

    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    @SerializedName(value = "project_id", alternate = "project")
    @Nullable
    Reference<Project> getProject();

    @SerializedName(value = "expense_category_id", alternate = "expense_category")
    @Nullable
    Reference<ExpenseCategory> getExpenseCategory();

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

    @Nullable
    LocalDate getSpentDate();

}
