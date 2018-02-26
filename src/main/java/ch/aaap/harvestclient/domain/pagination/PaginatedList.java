package ch.aaap.harvestclient.domain.pagination;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.*;

/**
 * Holds any type of list from Harvest. We use the fact that missing JSON fields
 * will be set to null
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface PaginatedList {

    List<Client> getClients();

    List<Task> getTasks();

    List<ClientContact> getContacts();

    List<Project> getProjects();

    List<ProjectAssignment> getProjectAssignments();

    List<Role> getRoles();

    List<TaskAssignment> getTaskAssignments();

    List<TimeEntry> getTimeEntries();

    List<User> getUsers();

    List<Estimate> getEstimates();

    List<EstimateItem.Category> getEstimateItemCategories();

    List<EstimateMessage> getEstimateMessages();

    List<InvoiceItem.Category> getInvoiceItemCategories();

    @Nullable
    Integer getPerPage();

    @Nullable
    Integer getTotalPages();

    @Nullable
    Integer getNextPage();

    @Nullable
    Integer getPreviousPage();

    @Nullable
    Integer getPage();

    @Nullable
    PaginationLinks getLinks();

}
