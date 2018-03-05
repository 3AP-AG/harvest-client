package util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.Api;
import ch.aaap.harvestclient.api.filter.*;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.ClientContact;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

public class DeleteAllData {

    private static final Logger log = LoggerFactory.getLogger(DeleteAllData.class);

    /**
     * Delete all available data in an Harvest account apart from the currently
     * authenticated User
     *
     */
    public static void main(String[] args) {

        Harvest harvest = TestSetupUtil.getAnotherAdminAccess();

        log.debug("Deleting all data in account {}", harvest.getAccountId());

        harvest.projects().list(new ProjectFilter()).forEach(harvest.projects()::delete);
        List<ClientContact> clientContacts = harvest.clientContacts().list(new ClientContactFilter());
        deleteAll(clientContacts, harvest.clientContacts());

        harvest.estimates().list(new EstimateFilter()).forEach(harvest.estimates()::delete);
        harvest.estimateItemCategories().list(null).forEach(harvest.estimateItemCategories()::delete);

        List<Client> clients = harvest.clients().list(new ClientFilter());
        deleteAll(clients, harvest.clients());

        harvest.timesheets().list(new TimeEntryFilter()).forEach(harvest.timesheets()::delete);
        harvest.tasks().list(new TaskFilter()).forEach(harvest.tasks()::delete);

        harvest.roles().list().forEach(harvest.roles()::delete);

        User self = harvest.users().getSelf();
        harvest.users().list().stream()
                .filter(user -> !user.getId().equals(self.getId()))
                .forEach(harvest.users()::delete);

    }

    private static <T extends Reference<T>> void deleteAll(List<T> list, Api.Delete<T> api) {
        for (T t : list) {
            try {
                api.delete(t);
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
