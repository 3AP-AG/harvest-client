package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.api.filter.TeamReportFilter;
import ch.aaap.harvestclient.domain.TeamReport;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import java.util.List;

/**
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/reports-api/reports/time-reports/#team-report">
 *      Projects API on Harvest</a>
 */
public interface TeamReportsApi {

    List<TeamReport> list(TeamReportFilter filter);

    Pagination<TeamReport> list(TeamReportFilter filter, int page, int perPage);
}
