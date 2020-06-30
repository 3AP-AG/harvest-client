package ch.aaap.harvestclient.impl;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TeamReportsApi;
import ch.aaap.harvestclient.api.filter.TeamReportFilter;
import ch.aaap.harvestclient.domain.TeamReport;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.TestSetupUtil;

@HarvestTest
class TeamReportApiImplTest {

    @Test
    void testGet() {

        TeamReportsApi api = TestSetupUtil.getAdminAccess().teamReports();

        TeamReportFilter filter = TeamReportFilter.emptyFilter();
        filter.setFrom(LocalDate.now().minusYears(1));
        filter.setTo(LocalDate.now().minusDays(1));
        List<TeamReport> teamReports = api.list(filter);

        assertThat(teamReports).isNotNull();
        assertThat(teamReports.size()).isGreaterThan(0);

    }
}