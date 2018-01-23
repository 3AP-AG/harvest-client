package service;

import core.Harvest;
import domain.TimeEntries;
import domain.TimeEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeEntryServiceTest {

    private static TimeEntryService service;
    private static Harvest harvest;


    @BeforeAll
    public static void setup() {
        harvest = new Harvest();
        service = harvest.getTimeEntryService();

    }

    @Test
    public void testListAll() throws IOException {

        Call<TimeEntries> call = service.listAll();
        Response<TimeEntries> response = call.execute();
        List<TimeEntry> entries = response.body().getEntries();
        assertTrue(entries.size() > 0);
        for (TimeEntry entry : entries) {
            System.out.println(entry);
        }

    }

}