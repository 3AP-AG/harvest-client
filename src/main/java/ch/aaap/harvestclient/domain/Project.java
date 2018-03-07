package ch.aaap.harvestclient.domain;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Project extends BaseObject<Project> {

    enum BillingMethod {

        @SerializedName("Project")
        PROJECT,

        @SerializedName("Tasks")
        TASKS,

        @SerializedName("People")
        PEOPLE,

        @SerializedName("none")
        NONE
    }

    enum BudgetMethod {

        @SerializedName("project")
        HOURS_PER_PROJECT,

        @SerializedName("project_cost")
        TOTAL_PROJECT_FEES,

        @SerializedName("task")
        HOURS_PER_TASK,

        @SerializedName("person")
        HOURS_PER_PERSON,

        @SerializedName("none")
        NO_BUDGET

    }

    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    String getName();

    @Nullable
    String getCode();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

    @SerializedName("is_billable")
    Boolean getBillable();

    /**
     * @return Whether the project is a fixed-fee project or not. Setting this to
     *         true at creation will set billBy to NONE, regardless of input
     */
    @SerializedName("is_fixed_fee")
    @Nullable
    Boolean getFixedFee();

    BillingMethod getBillBy();

    /**
     * @return Rate for projects when billedBy is {@link BillingMethod#PROJECT}
     */
    @Nullable
    Double getHourlyRate();

    /**
     * @return Budget in hours for project when budgetBy is
     *         {@link BudgetMethod#HOURS_PER_PROJECT}
     */
    @Nullable
    Double getBudget();

    BudgetMethod getBudgetBy();

    @Nullable
    Boolean getNotifyWhenOverBudget();

    @Nullable
    Double getOverBudgetNotificationPercentage();

    @Nullable
    LocalDate getOverBudgetNotificationDate();

    /**
     * @return Option to show project budget to all employees. Does not apply to
     *         Total Project Fee projects. Defaults to false.
     */
    @Nullable
    Boolean getShowBudgetToAll();

    /**
     * @return The monetary budget for the project when budgetBy is
     *         {@link BudgetMethod#TOTAL_PROJECT_FEES}
     */
    @Nullable
    Double getCostBudget();

    /**
     * @return Option for budget of Total Project Fees projects to include tracked
     *         expenses. Defaults to false.
     */
    @Nullable
    Boolean getCostBudgetIncludeExpenses();

    /**
     * @return The amount you plan to invoice for the project. Only used by
     *         fixed-fee projects.
     */
    @Nullable
    Double getFee();

    /**
     * max length = 65,535
     * 
     * @return the current value
     */
    @Nullable
    String getNotes();

    @Nullable
    LocalDate getStartsOn();

    @Nullable
    LocalDate getEndsOn();

}
