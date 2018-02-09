package ch.aaap.harvestclient.impl.project;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ProjectsApi;
import ch.aaap.harvestclient.api.filter.ProjectFilter;
import ch.aaap.harvestclient.domain.Project;
import util.TestSetupUtil;

@HarvestTest
class ProjectsApiListTest {

    private static ProjectsApi projectsApi = TestSetupUtil.getAdminAccess().projects();

    @Test
    void list() {

        List<Project> projects = projectsApi.list(ProjectFilter.emptyFilter());

        assertThat(projects).isNotEmpty();
    }

}