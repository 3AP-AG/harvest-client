package ch.aaap.harvestclient.impl.project;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ImmutableProject;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ProjectsApiListTest {

    public static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final ProjectsApi projectsApi = harvest.projects();
    private static final Reference<Client> client = ExistingData.getInstance().getClientReference();
    private static final Reference<Client> anotherClient = ExistingData.getInstance().getAnotherClientReference();
    private Project project;

    @AfterEach
    void afterEach() {
        if (project != null) {
            harvest.projects().delete(project);
        }
    }

    @Test
    void list() {

        List<Project> projects = projectsApi.list(ProjectFilter.emptyFilter());

        assertThat(projects).isNotEmpty();
    }

    @Test
    void listPaginated() {

        Pagination<Project> pagination = projectsApi.list(ProjectFilter.emptyFilter(), 1, 1);

        List<Project> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByUpdated() {
        ProjectFilter filter = new ProjectFilter();
        filter.setUpdatedSince(Instant.now());
        List<Project> projects = projectsApi.list(filter);

        assertThat(projects).isEmpty();

    }

    @Test
    void listByActive(TestInfo testInfo) {

        project = projectsApi.create(ImmutableProject.builder()
                .name("Project for " + testInfo.getDisplayName())
                .billable(true)
                .billBy(Project.BillingMethod.PROJECT)
                .budgetBy(Project.BudgetMethod.HOURS_PER_PROJECT)
                .active(false)
                .client(ExistingData.getInstance().getClientReference())
                .build());

        ProjectFilter filter = new ProjectFilter();
        filter.setActive(false);
        List<Project> projects = projectsApi.list(filter);

        assertThat(projects).containsExactly(project);

    }

    @Test
    void listByClient(TestInfo testInfo) {

        project = projectsApi.create(ImmutableProject.builder()
                .name("Project for " + testInfo.getDisplayName())
                .billable(true)
                .billBy(Project.BillingMethod.PROJECT)
                .budgetBy(Project.BudgetMethod.HOURS_PER_PROJECT)
                .client(anotherClient)
                .build());

        ProjectFilter filter = new ProjectFilter();
        filter.setClientReference(client);

        List<Project> projects = projectsApi.list(filter);
        assertThat(projects).doesNotContain(project);

    }
}