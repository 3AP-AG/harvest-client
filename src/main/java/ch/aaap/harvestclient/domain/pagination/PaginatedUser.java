package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import ch.aaap.harvestclient.domain.User;

public class PaginatedUser extends PaginatedList {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "PaginatedUser{" +
                "users=" + users +
                ", perPage=" + perPage +
                ", totalPages=" + totalPages +
                ", nextPage=" + nextPage +
                ", previousPage=" + previousPage +
                ", page=" + page +
                ", paginationLinks=" + paginationLinks +
                '}';
    }
}
