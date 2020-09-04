package ch.aaap.harvestclient.api.filter;

import ch.aaap.harvestclient.api.filter.base.ListFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.reference.Reference;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TeamReportFilter implements ListFilter {
    private LocalDate from;
    private LocalDate to;

    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();

        if (from != null) {
            map.put("from", localDateToString(from));
        }
        if (to != null) {
            map.put("to", localDateToString(to));
        }

        return map;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "TeamReportFilter{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    public static TeamReportFilter emptyFilter() {
        return new TeamReportFilter();
    }


    private String localDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}
