package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.Role;

public class PaginatedRole extends PaginatedList {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PaginatedRole{" +
                "roles=" + roles +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
