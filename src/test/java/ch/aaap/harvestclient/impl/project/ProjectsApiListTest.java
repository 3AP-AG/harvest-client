package ch.aaap.harvestclient.impl.project;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import util.TestSetupUtil;

@HarvestTest
class ProjectsApiListTest {

    private static final ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

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
}