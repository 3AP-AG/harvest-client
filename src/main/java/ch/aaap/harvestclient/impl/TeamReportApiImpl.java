package ch.aaap.harvestclient.impl;

import ch.aaap.harvestclient.api.TeamReportsApi;
import ch.aaap.harvestclient.api.filter.TeamReportFilter;
import ch.aaap.harvestclient.domain.TeamReport;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.service.TeamReportService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;

public class TeamReportApiImpl implements TeamReportsApi {

  private final TeamReportService teamReportService;
  private static final Logger log = LoggerFactory.getLogger(RolesApiImpl.class);

  public TeamReportApiImpl(TeamReportService teamReportService) {
    this.teamReportService = teamReportService;
  }

  @Override
  public List<TeamReport> list(TeamReportFilter filter) {
    log.debug("Getting team report for period {} - {}", filter.getFrom(), filter.getTo());
    return Common.collect((page, perPage) -> list(filter, page, perPage));
  }

  @Override
  public Pagination<TeamReport> list(TeamReportFilter filter, int page, int perPage) {
    log.debug("Getting team report {} of Task list", page);
    Call<PaginatedList> call = teamReportService.list(filter.toMap(page, perPage));
    PaginatedList pagination = ExceptionHandler.callOrThrow(call);
    return Pagination.of(pagination, pagination.getTeamReport());
  }
}
