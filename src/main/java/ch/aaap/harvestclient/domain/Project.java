package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

public class Project implements Reference<Project> {

    public enum BillingMethod {

        @SerializedName("Project")
        PROJECT,

        @SerializedName("Tasks")
        TASKS,

        @SerializedName("People")
        PEOPLE,

        @SerializedName("none")
        NONE
    }

    public enum BudgetMethod {

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

    private Long id;

    @SerializedName("client")
    private ClientReferenceDto clientReference;

    private String name;

    private String code;

    @SerializedName("is_active")
    private Boolean active;

    @SerializedName("is_billable")
    private Boolean billable;

    /**
     * Whether the project is a fixed-fee project or not. Setting this to true at
     * creation will set billBy to NONE, regardless of input
     */
    @SerializedName("is_fixed_fee")
    private Boolean fixedFee;

    private BillingMethod billBy;

    /**
     * Rate for projects when billedBy is {@link BillingMethod#PROJECT}
     */
    private Double hourlyRate;
    /**
     * Budget in hours for project when budgetBy is
     * {@link BudgetMethod#HOURS_PER_PROJECT}
     */
    private Double budget;

    private BudgetMethod budgetBy;
    private Boolean notifyWhenOverBudget;
    private Double overBudgetNotificationPercentage;
    private LocalDate overBudgetNotificationDate;

    /**
     * Option to show project budget to all employees. Does not apply to Total
     * Project Fee projects. Defaults to false.
     */
    private Boolean showBudgetToAll;
    /**
     * The monetary budget for the project when budgetBy is
     * {@link BudgetMethod#TOTAL_PROJECT_FEES}
     */
    private Double costBudget;

    /**
     * Option for budget of Total Project Fees projects to include tracked expenses.
     * Defaults to false.
     */
    private Boolean costBudgetIncludeExpenses;

    /**
     * The amount you plan to invoice for the project. Only used by fixed-fee
     * projects.
     */
    private Double fee;
    private String notes;

    private LocalDate startsOn;
    private LocalDate endsOn;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public ClientReferenceDto getClientReference() {
        return clientReference;
    }

    public void setClientReference(ClientReferenceDto clientReference) {
        this.clientReference = clientReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean getFixedFee() {
        return fixedFee;
    }

    public void setFixedFee(Boolean fixedFee) {
        this.fixedFee = fixedFee;
    }

    public BillingMethod getBillBy() {
        return billBy;
    }

    public void setBillBy(BillingMethod billBy) {
        this.billBy = billBy;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public BudgetMethod getBudgetBy() {
        return budgetBy;
    }

    public void setBudgetBy(BudgetMethod budgetBy) {
        this.budgetBy = budgetBy;
    }

    public Boolean getNotifyWhenOverBudget() {
        return notifyWhenOverBudget;
    }

    public void setNotifyWhenOverBudget(Boolean notifyWhenOverBudget) {
        this.notifyWhenOverBudget = notifyWhenOverBudget;
    }

    public Double getOverBudgetNotificationPercentage() {
        return overBudgetNotificationPercentage;
    }

    public void setOverBudgetNotificationPercentage(Double overBudgetNotificationPercentage) {
        this.overBudgetNotificationPercentage = overBudgetNotificationPercentage;
    }

    public LocalDate getOverBudgetNotificationDate() {
        return overBudgetNotificationDate;
    }

    public void setOverBudgetNotificationDate(LocalDate overBudgetNotificationDate) {
        this.overBudgetNotificationDate = overBudgetNotificationDate;
    }

    public Boolean getShowBudgetToAll() {
        return showBudgetToAll;
    }

    public void setShowBudgetToAll(Boolean showBudgetToAll) {
        this.showBudgetToAll = showBudgetToAll;
    }

    public Double getCostBudget() {
        return costBudget;
    }

    public void setCostBudget(Double costBudget) {
        this.costBudget = costBudget;
    }

    public Boolean getCostBudgetIncludeExpenses() {
        return costBudgetIncludeExpenses;
    }

    public void setCostBudgetIncludeExpenses(Boolean costBudgetIncludeExpenses) {
        this.costBudgetIncludeExpenses = costBudgetIncludeExpenses;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(LocalDate startsOn) {
        this.startsOn = startsOn;
    }

    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", clientReference=" + clientReference +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                ", billable=" + billable +
                ", fixedFee=" + fixedFee +
                ", billBy='" + billBy + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", budget=" + budget +
                ", budgetBy='" + budgetBy + '\'' +
                ", notifyWhenOverBudget=" + notifyWhenOverBudget +
                ", overBudgetNotificationPercentage=" + overBudgetNotificationPercentage +
                ", overBudgetNotificationDate=" + overBudgetNotificationDate +
                ", showBudgetToAll=" + showBudgetToAll +
                ", costBudget=" + costBudget +
                ", costBudgetIncludeExpenses=" + costBudgetIncludeExpenses +
                ", fee=" + fee +
                ", notes='" + notes + '\'' +
                ", startsOn=" + startsOn +
                ", endsOn=" + endsOn +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
