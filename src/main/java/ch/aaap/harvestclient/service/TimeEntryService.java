package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfoTimestamp;
import ch.aaap.harvestclient.domain.param.TimeEntryUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface TimeEntryService {

    @GET("time_entries")
    Call<PaginatedList> list(@QueryMap() Map<String, Object> options);

    @GET("time_entries/{entryId}")
    Call<TimeEntry> get(@Path("entryId") long entryId);

    @POST("time_entries")
    Call<TimeEntry> create(@Body TimeEntryCreationInfoDuration creationInfo);

    @POST("time_entries")
    Call<TimeEntry> create(@Body TimeEntryCreationInfoTimestamp creationInfo);

    @PATCH("time_entries/{entryId}")
    Call<TimeEntry> update(@Path("entryId") long entryId, @Body TimeEntryUpdateInfo toChange);

    @DELETE("time_entries/{entryId}")
    Call<Void> delete(@Path("entryId") long entryId);

    @PATCH("time_entries/{entryId}/restart")
    Call<TimeEntry> restart(@Path("entryId") long entryId);

    @PATCH("time_entries/{entryId}/stop")
    Call<TimeEntry> stop(@Path("entryId") long entryId);
}
