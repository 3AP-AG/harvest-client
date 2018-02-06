package ch.aaap.harvestclient.impl.project;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import ch.aaap.harvestclient.domain.reference.ClientReference;
import util.TestSetupUtil;

public class ProjectsApiCreateTest {

    private static ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

    /**
     * Project that get deleted automatically after the test
     */
    private Project project;

    @AfterEach
    void afterEach() {
        if (project != null) {
            projectsApi.delete(project);
            project = null;
        }
    }

    @Test
    void createDefault(TestInfo testInfo) {

        ClientReference clientReference = TestSetupUtil.getExistingClient();
        String name = "Project for test " + testInfo.getDisplayName();
        boolean billable = true;
        Project.BillingMethod billBy = Project.BillingMethod.PROJECT;
        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PROJECT;

        ProjectCreationInfo creationInfo = new ProjectCreationInfo(clientReference, name, billable, billBy, budgetBy);
        project = projectsApi.create(creationInfo);

        assertThat(project.getBillable()).isEqualTo(billable);
        assertThat(project.getBillBy()).isEqualTo(billBy);
        assertThat(project.getBudgetBy()).isEqualTo(budgetBy);
        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getClientReference().getId()).isEqualTo(clientReference.getId());

    }

    @Test
    void createAllOptions(TestInfo testInfo) {

        ClientReference clientReference = TestSetupUtil.getExistingClient();
        String name = "Project for test " + testInfo.getDisplayName();
        boolean billable = true;
        Project.BillingMethod billBy = Project.BillingMethod.PROJECT;
        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PROJECT;

        String code = "testCode";
        boolean active = false;
        // see other test for fixedFee = true
        boolean fixedFee = false;
        double hourlyRate = 240;
        double budget = 120;
        boolean notifyWhenOverBudget = true;
        double overBudgetNotificationPercentage = 90.;
        boolean showBudgetToAll = true;
        double costBudget = 2000;
        boolean costBudgetIncludeExpenses = true;
        double fee = 5000;
        String notes = "test notes";
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(3).plusDays(2);

        ProjectCreationInfo creationInfo = new ProjectCreationInfo(clientReference, name, billable, billBy, budgetBy);
        creationInfo.setCode(code);
        creationInfo.setActive(active);
        creationInfo.setFixedFee(fixedFee);
        creationInfo.setHourlyRate(hourlyRate);
        creationInfo.setBudget(budget);
        creationInfo.setNotifyWhenOverBudget(notifyWhenOverBudget);
        creationInfo.setOverBudgetNotificationPercentage(overBudgetNotificationPercentage);
        creationInfo.setShowBudgetToAll(showBudgetToAll);
        creationInfo.setCostBudget(costBudget);
        creationInfo.setCostBudgetIncludeExpenses(costBudgetIncludeExpenses);
        creationInfo.setFee(fee);
        creationInfo.setNotes(notes);
        creationInfo.setStartsOn(start);
        creationInfo.setEndsOn(end);

        project = projectsApi.create(creationInfo);

        assertThat(project).isEqualToIgnoringGivenFields(creationInfo, "clientId", "id", "createdAt", "updatedAt",
                "clientReference", "fee");
        // fee can only be set by having fixed_fee = true
        assertThat(project.getFee()).isNull();
        assertThat(project.getClientReference().getId()).isEqualTo(clientReference.getId());
        assertThat(project.getCreatedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.MINUTES));
        assertThat(project.getUpdatedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.MINUTES));

    }

    @Test
    void createFixedFee(TestInfo testInfo) {

        ClientReference clientReference = TestSetupUtil.getExistingClient();
        String name = "Project for test " + testInfo.getDisplayName();
        boolean billable = true;
        Project.BillingMethod billBy = Project.BillingMethod.TASKS;
        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PROJECT;

        String code = "testCode";
        boolean active = false;
        boolean fixedFee = true;
        double hourlyRate = 240;
        double budget = 120;
        boolean notifyWhenOverBudget = true;
        double overBudgetNotificationPercentage = 90.;
        boolean showBudgetToAll = true;
        double costBudget = 2000;
        boolean costBudgetIncludeExpenses = true;
        double fee = 5000;
        String notes = "test notes";
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(3).plusDays(2);

        ProjectCreationInfo creationInfo = new ProjectCreationInfo(clientReference, name, billable, billBy, budgetBy);
        creationInfo.setCode(code);
        creationInfo.setActive(active);
        creationInfo.setFixedFee(fixedFee);
        creationInfo.setHourlyRate(hourlyRate);
        creationInfo.setBudget(budget);
        creationInfo.setNotifyWhenOverBudget(notifyWhenOverBudget);
        creationInfo.setOverBudgetNotificationPercentage(overBudgetNotificationPercentage);
        creationInfo.setShowBudgetToAll(showBudgetToAll);
        creationInfo.setCostBudget(costBudget);
        creationInfo.setCostBudgetIncludeExpenses(costBudgetIncludeExpenses);
        creationInfo.setFee(fee);
        creationInfo.setNotes(notes);
        creationInfo.setStartsOn(start);
        creationInfo.setEndsOn(end);

        project = projectsApi.create(creationInfo);

        assertThat(project).isEqualToIgnoringGivenFields(creationInfo, "clientId", "id", "createdAt", "updatedAt",
                "clientReference", "billBy");

        // setting fixed_fee to true changes the billing method to None
        assertThat(project.getBillBy()).isEqualTo(Project.BillingMethod.NONE);
        assertThat(project.getClientReference().getId()).isEqualTo(clientReference.getId());
    }
}
