package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.Project;

public class PaginatedProject extends PaginatedList {

    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "PaginatedProject{" +
                "projects=" + projects +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
