package ch.aaap.harvestclient.service;

import ch.aaap.harvestclient.domain.TimeEntries;
import ch.aaap.harvestclient.domain.TimeEntry;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TimeEntryService {

    @GET("time_entries")
    Call<TimeEntries> listAll();

    @GET("time_entries/{entryId}")
    Call<TimeEntry> get(@Path("entryId") long entryId);

    @POST("time_entries")
    void add(TimeEntry timeEntry);

    @PATCH("time_entries/{entryId}")
    void patch(@Path("entryId") long entryId);
}
