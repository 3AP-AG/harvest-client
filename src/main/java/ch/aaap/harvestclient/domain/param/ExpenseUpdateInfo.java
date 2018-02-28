package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.ExpenseCommon;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ExpenseUpdateInfo extends ExpenseCommon {

    @Override
    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    @Override
    @SerializedName(value = "project_id", alternate = "project")
    @Nullable
    Reference<Project> getProject();

    @Override
    @SerializedName(value = "expense_category_id", alternate = "expense_category")
    @Nullable
    Reference<ExpenseCategory> getExpenseCategory();

    /**
     * Set to true to delete an attached receipt
     */
    @Nullable
    Boolean getDeleteReceipt();

}
