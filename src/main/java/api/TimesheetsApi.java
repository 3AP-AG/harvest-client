package api;

import java.time.LocalDate;
import java.util.List;

import domain.TimeEntry;

public interface TimesheetsApi {

    List<TimeEntry> list();

    TimeEntry get(long timeEntryId);

    void create(long projectId, long taskId, LocalDate spentDate);

    void create(long projectId, long taskId, LocalDate spentDate, long userId);

    void delete(long timeEntryId);
}
