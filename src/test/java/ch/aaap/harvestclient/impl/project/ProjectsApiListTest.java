package ch.aaap.harvestclient.impl.project;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectListFilter;
import ch.aaap.harvestclient.domain.Project;
import util.TestSetupUtil;

class ProjectsApiListTest {

    private static ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

    @Test
    void list() {

        List<Project> projects = projectsApi.list(ProjectListFilter.emptyFilter());

        assertThat(projects).isNotEmpty();
    }

}