package ch.aaap.harvestclient.impl.project;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableProject;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.param.ImmutableProjectUpdateInfo;
import ch.aaap.harvestclient.domain.param.ProjectUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ProjectsApiUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectsApiUpdateTest.class);

    private final ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

    /**
     * A project newly created for each test
     */
    private Project project;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {

        Reference<Client> clientReference = ExistingData.getInstance().getClientReference();
        String name = "Project for test " + testInfo.getDisplayName();
        boolean billable = true;
        Project.BillingMethod billBy = Project.BillingMethod.PROJECT;
        Project.BudgetMethod budgetBy = Project.BudgetMethod.HOURS_PER_PROJECT;

        Project creationInfo = ImmutableProject.builder()
                .client(clientReference)
                .name(name)
                .billable(billable)
                .billBy(billBy)
                .budgetBy(budgetBy)
                .build();
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

        ProjectUpdateInfo info = ImmutableProjectUpdateInfo.builder()
                .name("new Name for Project")
                .build();

        project = projectsApi.update(project, info);

        assertThat(project.getName()).isEqualTo(info.getName());
    }

    @Test
    @Disabled("Harvest bug change project budgetBy")
    void changeBudgetBy() {

        assertThat(project.getBudgetBy()).isEqualTo(Project.BudgetMethod.HOURS_PER_PROJECT);

        ProjectUpdateInfo info = ImmutableProjectUpdateInfo.builder()
                .budgetBy(Project.BudgetMethod.HOURS_PER_PERSON)
                .hourlyRate(240.)
                .budget(120.)
                .notifyWhenOverBudget(true)
                .overBudgetNotificationPercentage(90.)
                .showBudgetToAll(true)
                .costBudget(2000.)
                .costBudgetIncludeExpenses(true)
                .build();

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToIgnoringNullFields(info);
    }

    @Test
    @Disabled("Harvest bug change project budgetBy")
    void changeBudgetByLess() {

        ProjectUpdateInfo info = ImmutableProjectUpdateInfo.builder()
                .budgetBy(Project.BudgetMethod.HOURS_PER_PERSON)
                .build();

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToIgnoringNullFields(info);
    }

    @Test
    void changeAllSmallDetails() {

        Reference<Client> clientReference = ExistingData.getInstance().getAnotherClientReference();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(3).plusDays(2);

        ProjectUpdateInfo info = ImmutableProjectUpdateInfo.builder()
                .client(clientReference)
                .billable(true)
                .name("new Name for Project")
                .code("code")
                .active(false)
                // see other test for fixedFee = true
                .fixedFee(false)
                .hourlyRate(240.)
                .budget(120.)
                .notifyWhenOverBudget(true)
                .overBudgetNotificationPercentage(90.)
                .showBudgetToAll(true)
                .costBudget(2000.)
                // can only change this for TOTAL_PROJECT_FEES projects
                // .costBudgetIncludeExpenses(true)
                .notes("test notes")
                .startsOn(start)
                .endsOn(end)
                .build();

        project = projectsApi.update(project, info);

        assertThat(project).isEqualToComparingOnlyGivenFields(info,
                "billable", "name", "code", "active", "fixedFee", "hourlyRate",
                "budget", "notifyWhenOverBudget", "overBudgetNotificationPercentage", "showBudgetToAll", "costBudget",
                "notes", "startsOn", "endsOn");

        assertThat(project.getClient().getId()).isEqualTo(info.getClient().getId());

    }
}
