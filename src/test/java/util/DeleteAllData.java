package util;

import ch.aaap.harvestclient.api.filter.*;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.User;

public class DeleteAllData {

    /**
     * Delete all available data in an Harvest account apart from the currently
     * authenticated User
     *
     */
    public static void main(String[] args) {

        Harvest harvest = TestSetupUtil.getAdminAccess();

        harvest.projects().list(new ProjectFilter()).forEach(harvest.projects()::delete);
        harvest.clientContacts().list(new ClientContactFilter()).forEach(harvest.clientContacts()::delete);

        harvest.estimates().list(new EstimateFilter()).forEach(harvest.estimates()::delete);
        harvest.estimateItemCategories().list(null).forEach(harvest.estimateItemCategories()::delete);

        harvest.clients().list(new ClientFilter()).forEach(harvest.clients()::delete);

        harvest.timesheets().list(new TimeEntryFilter()).forEach(harvest.timesheets()::delete);
        harvest.tasks().list(new TaskFilter()).forEach(harvest.tasks()::delete);

        harvest.roles().list().forEach(harvest.roles()::delete);

        User self = harvest.users().getSelf();
        harvest.users().list().stream()
                .filter(user -> !user.getId().equals(self.getId()))
                .forEach(harvest.users()::delete);

    }
}
