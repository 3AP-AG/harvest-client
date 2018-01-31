package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.pagination.PaginatedTimeEntry;
import ch.aaap.harvestclient.domain.param.TimeEntryCreationInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface TimeEntryService {

    @GET("time_entries")
    Call<PaginatedTimeEntry> list(@QueryMap() Map<String, Object> options);

    @GET("time_entries/{entryId}")
    Call<TimeEntry> get(@Path("entryId") long entryId);

    @POST("time_entries")
    Call<TimeEntry> create(@Body TimeEntryCreationInfo creationInfo);

    @PATCH("time_entries/{entryId}")
    void patch(@Path("entryId") long entryId);

    @DELETE("time_entries/{entryId}")
    Call<Void> delete(@Path("entryId") long entryId);
}
