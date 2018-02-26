package ch.aaap.harvestclient.api.filter;

import java.time.LocalDate;
import java.util.Map;

import ch.aaap.harvestclient.api.filter.base.ClientAndUpdatedFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

public class InvoiceFilter extends ClientAndUpdatedFilter {

    private Reference<Project> projectReference;

    private LocalDate from;

    private LocalDate to;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        if (projectReference != null) {
            map.put("project_id", projectReference.getId());
        }
        if (from != null) {
            map.put("from", from);
        }
        if (to != null) {
            map.put("to", to);
        }
        return map;
    }

    public Reference<Project> getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(
            Reference<Project> projectReference) {
        this.projectReference = projectReference;
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
}
