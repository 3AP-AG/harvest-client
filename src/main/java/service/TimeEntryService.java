package service;

import domain.TimeEntries;
import domain.TimeEntry;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeEntryService {

    @GET("time_entries")
    Call<TimeEntries> listAll();
}
