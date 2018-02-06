package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.ProjectAssignment;

public class PaginatedProjectAssignment extends PaginatedList {

    private List<ProjectAssignment> projectAssignments;

    public List<ProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(List<ProjectAssignment> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }

    @Override
    public String toString() {
        return "PaginatedProjectAssignment{" +
                "projectAssignments=" + projectAssignments +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
