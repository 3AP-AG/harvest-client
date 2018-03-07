package ch.aaap.harvestclient.domain;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Expense extends BaseObject<Expense>, ExpenseCommon {

    @SerializedName(value = "user_id", alternate = "user")
    @Nullable
    Reference<User> getUser();

    @Override
    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProject();

    @Override
    @SerializedName(value = "expense_category_id", alternate = "expense_category")
    Reference<ExpenseCategory> getExpenseCategory();

    @Override
    LocalDate getSpentDate();

}
