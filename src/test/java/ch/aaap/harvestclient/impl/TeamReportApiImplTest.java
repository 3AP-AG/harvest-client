package ch.aaap.harvestclient.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TeamReportsApi;
import ch.aaap.harvestclient.api.filter.TeamReportFilter;
import ch.aaap.harvestclient.domain.TeamReport;
import ch.aaap.harvestclient.exception.BadRequestException;
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
        filter.setFrom(LocalDate.now().minusYears(1).plusDays(1));
        filter.setTo(LocalDate.now());
        List<TeamReport> teamReports = api.list(filter);

        assertThat(teamReports).isNotNull();
        assertThat(teamReports.size()).isGreaterThan(0);

    }
    @Test
    void testReportForMoreThanOneYear() {
        assertThrows(BadRequestException.class, () -> {
            TeamReportsApi api = TestSetupUtil.getAdminAccess().teamReports();

            TeamReportFilter filter = TeamReportFilter.emptyFilter();
            filter.setFrom(LocalDate.now().minusYears(1));
            filter.setTo(LocalDate.now().plusDays(1));
            given(api.list(filter)).willThrow(new Exception());
        });
    }
}
