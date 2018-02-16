package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ProjectUpdateInfo {

    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    @Nullable
    String getName();

    @Nullable
    String getCode();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

    @SerializedName("is_billable")
    @Nullable
    Boolean getBillable();

    /**
     * Whether the project is a fixed-fee project or not. Setting this to true at
     * creation will set billBy to NONE, regardless of input
     */
    @SerializedName("is_fixed_fee")
    @Nullable
    Boolean getFixedFee();

    @Nullable
    Project.BillingMethod getBillBy();

    /**
     * Rate for projects when billedBy is {@link Project.BillingMethod#PROJECT}
     */
    @Nullable
    Double getHourlyRate();

    /**
     * Budget in hours for project when budgetBy is
     * {@link Project.BudgetMethod#HOURS_PER_PROJECT}
     */
    @Nullable
    Double getBudget();

    @Nullable
    Project.BudgetMethod getBudgetBy();

    @Nullable
    Boolean getNotifyWhenOverBudget();

    @Nullable
    Double getOverBudgetNotificationPercentage();

    @Nullable
    LocalDate getOverBudgetNotificationDate();

    /**
     * Option to show project budget to all employees. Does not apply to Total
     * Project Fee projects. Defaults to false.
     */
    @Nullable
    Boolean getShowBudgetToAll();

    /**
     * The monetary budget for the project when budgetBy is
     * {@link Project.BudgetMethod#TOTAL_PROJECT_FEES}
     */
    @Nullable
    Double getCostBudget();

    /**
     * Option for budget of Total Project Fees projects to include tracked expenses.
     * Defaults to false.
     */
    @Nullable
    Boolean getCostBudgetIncludeExpenses();

    /**
     * The amount you plan to invoice for the project. Only used by fixed-fee
     * projects.
     */
    @Nullable
    Double getFee();

    @Nullable
    String getNotes();

    @Nullable
    LocalDate getStartsOn();

    @Nullable
    LocalDate getEndsOn();
}
