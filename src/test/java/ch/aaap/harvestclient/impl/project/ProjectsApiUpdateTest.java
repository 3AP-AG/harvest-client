package ch.aaap.harvestclient.impl.project;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.param.ProjectCreationInfo;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.TestSetupUtil;

@HarvestTest
public class ProjectsApiUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectsApiUpdateTest.class);

    private ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

    /**
     * A project newly created for each test
     */
    private Project project;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {

        Reference<Client> clientReference = TestSetupUtil.getExistingClient();
        String name = "Project for test " + testInfo.getDisplayName();
        boolean billable = true;
        Project.BillingMethod billBy = Project.BillingMethod.PROJECT;
        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PROJECT;

        ProjectCreationInfo creationInfo = new ProjectCreationInfo(clientReference, name, billable, billBy, budgetBy);
        project = projectsApi.create(creationInfo);
    }

    @AfterEach
    void afterEach() {
        if (project != null) {
            projectsApi.delete(project);
            project = null;
        }
    }

    @Test
    void changeName() {

        ProjectUpdateInfo info = new ProjectUpdateInfo();
        String newName = "new Name for Project";
        info.setName(newName);

        project = projectsApi.update(project, info);

        assertThat(project.getName()).isEqualTo(newName);
    }

    @Test
    @Disabled("Harvest bug change project budgetBy")
    void changeBudgetBy() {

        assertThat(project.getBudgetBy()).isEqualTo(Project.BudgetMethod.HOURS_PER_PROJECT);

        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PERSON;

        double hourlyRate = 240;
        double budget = 120;
        boolean notifyWhenOverBudget = true;
        double overBudgetNotificationPercentage = 90.;
        boolean showBudgetToAll = true;
        double costBudget = 2000;
        boolean costBudgetIncludeExpenses = true;

        ProjectUpdateInfo info = new ProjectUpdateInfo();
        info.setBudgetBy(budgetBy);

        info.setHourlyRate(hourlyRate);
        info.setBudget(budget);
        info.setNotifyWhenOverBudget(notifyWhenOverBudget);
        info.setOverBudgetNotificationPercentage(overBudgetNotificationPercentage);
        info.setShowBudgetToAll(showBudgetToAll);
        info.setCostBudget(costBudget);
        info.setCostBudgetIncludeExpenses(costBudgetIncludeExpenses);

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToIgnoringNullFields(info);
    }

    @Test
    @Disabled("Harvest bug change project budgetBy")
    void changeBudgetByLess() {

        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PERSON;

        ProjectUpdateInfo info = new ProjectUpdateInfo();
        info.setBudgetBy(budgetBy);

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToIgnoringNullFields(info);
    }

    @Test
    void changeAllSmallDetails() {

        Reference<Client> clientReference = TestSetupUtil.getExistingClient();
        boolean billable = true;

        String name = "new Name for Project";
        String code = "code";
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
        String notes = "test notes";
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(3).plusDays(2);

        ProjectUpdateInfo info = new ProjectUpdateInfo();
        info.setClientReference(clientReference);
        info.setBillable(billable);

        info.setName(name);
        info.setCode(code);
        info.setActive(active);
        info.setFixedFee(fixedFee);
        info.setHourlyRate(hourlyRate);
        info.setBudget(budget);
        info.setNotifyWhenOverBudget(notifyWhenOverBudget);
        info.setOverBudgetNotificationPercentage(overBudgetNotificationPercentage);
        info.setShowBudgetToAll(showBudgetToAll);
        info.setCostBudget(costBudget);
        info.setCostBudgetIncludeExpenses(costBudgetIncludeExpenses);
        info.setNotes(notes);
        info.setStartsOn(start);
        info.setEndsOn(end);

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToIgnoringNullFields(info);
    }
}
