package ch.aaap.harvestclient.service;

import ch.aaap.harvestclient.domain.TeamReport;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TeamReportService {
    @GET("/v2/reports/time/team")
    Call<PaginatedList> list(@QueryMap Map<String, Object> options);
}
