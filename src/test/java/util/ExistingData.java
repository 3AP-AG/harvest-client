package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.GenericReference;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ExistingData {

    /**
     * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
     */
    private static class LazyHolder1 {
        static final ExistingData INSTANCE = new ExistingData(TestSetupUtil.getAdminAccess(), 1);
    }

    private static class LazyHolder2 {
        static final ExistingData ANOTHER_INSTANCE = new ExistingData(TestSetupUtil.getAnotherAdminAccess(), 2);
    }

    private static final Logger log = LoggerFactory.getLogger(ExistingData.class);
    /**
     * Set this to true to generate new TestData and verify the current one
     */
    private static final boolean checkValid = false;

    private final Reference<Task> taskReference;
    private final Reference<Task> anotherTaskReference;

    private final Reference<Client> clientReference;
    private final Reference<Client> anotherClientReference;

    private final Reference<Project> projectReference;

    private final Reference<Project> anotherProjectReference;

    private final Reference<User> userReference;
    private final Reference<User> anotherUserReference;

    private final Reference<TimeEntry> timeEntryReference;

    private final EstimateItem.Category estimateItemCategory;
    private final EstimateItem.Category anotherEstimateItemCategory;

    private final InvoiceItem.Category invoiceItemCategory;
    private final InvoiceItem.Category anotherInvoiceItemCategory;

    private final ProjectAssignment projectAssignment;

    private final Reference<ExpenseCategory> expenseCategory;

    private ExistingData(Harvest harvest, int accountNumber) {
        try {
            log.debug("Getting Existing Data for tests");

            TestData data = loadFromFile(accountNumber);
            if (checkValid) {
                new TestDataCreator(harvest).getOrCreateAll(data);
            }
            log.info("TestData is {}", data);

            taskReference = GenericReference.of(data.getTaskId());
            anotherTaskReference = GenericReference.of(data.getAnotherTaskId());

            clientReference = GenericReference.of(data.getClientId());
            anotherClientReference = GenericReference.of(data.getAnotherClientId());

            projectReference = GenericReference.of(data.getProjectId());
            anotherProjectReference = GenericReference.of(data.getAnotherProjectId());

            userReference = GenericReference.of(data.getUserId());
            anotherUserReference = GenericReference.of(data.getAnotherUserId());

            timeEntryReference = GenericReference.of(data.getTimeEntryId());

            // default categories
            estimateItemCategory = ImmutableEstimateItem.Category.builder().name("Product").build();
            anotherEstimateItemCategory = ImmutableEstimateItem.Category.builder().name("Service").build();

            // default categories
            invoiceItemCategory = ImmutableInvoiceItem.Category.builder().name("Product").build();
            anotherInvoiceItemCategory = ImmutableInvoiceItem.Category.builder().name("Service").build();

            expenseCategory = GenericReference.of(data.getExpenseCategoryId());

            // create assignment
            harvest.userAssignments().create(projectReference, ImmutableUserAssignment.builder()
                    .user(userReference).build());
            projectAssignment = harvest.projectAssignments().list(userReference).get(0);

        } catch (Throwable t) {
            log.error("", t);
            throw t;
        }
    }

    private TestData loadFromFile(int accountNumber) {
        TestData data = new TestData();
        log.debug("Loading data for account {}", accountNumber);
        // default account, 24h format
        if (accountNumber == 1) {
            // this could be saved to disk
            // for now it is just a bit of copy paste when setting up a new Harvest account
            data.setTimeEntryId(772005972);
            data.setUserId(2082952);
            data.setAnotherUserId(2082953);

            data.setClientId(6675516);
            data.setAnotherClientId(6675517);

            data.setClientContactId(5119206);
            data.setAnotherClientContactId(5119207);
            data.setProjectId(16987728);
            data.setAnotherProjectId(16987729);
            data.setTaskId(9562469);
            data.setAnotherTaskId(9562470);
            data.setTaskAssignmentId(183755758);
            data.setExpenseCategoryId(4793096);
        }
        // second account, 12h format
        else if (accountNumber == 2) {

            data.setTimeEntryId(772669116);
            data.setUserId(2084141);
            data.setAnotherUserId(2084142);

            data.setClientId(6679480);
            data.setAnotherClientId(6679481);

            data.setClientContactId(5120986);
            data.setAnotherClientContactId(5120987);
            data.setProjectId(17004414);
            data.setAnotherProjectId(17004415);
            data.setTaskId(9569328);
            data.setAnotherTaskId(9569329);
            data.setTaskAssignmentId(183909577);
            data.setExpenseCategoryId(4795806);
        }
        return data;
    }

    public static ExistingData getInstance() {
        return LazyHolder1.INSTANCE;
    }

    public static ExistingData getAnotherInstance() {
        return LazyHolder2.ANOTHER_INSTANCE;
    }

    public Reference<User> getUnassignedUser() {
        return anotherUserReference;
    }

    public Reference<Task> getUnassignedTask() {
        return anotherTaskReference;
    }

    public Reference<Task> getTaskReference() {
        return taskReference;
    }

    public Reference<Task> getAnotherTaskReference() {
        return anotherTaskReference;
    }

    public Reference<Client> getClientReference() {
        return clientReference;
    }

    public Reference<Client> getAnotherClientReference() {
        return anotherClientReference;
    }

    public Reference<Project> getProjectReference() {
        return projectReference;
    }

    public Reference<TimeEntry> getTimeEntryReference() {
        return timeEntryReference;
    }

    public EstimateItem.Category getEstimateItemCategory() {
        return estimateItemCategory;
    }

    public Reference<User> getUserReference() {
        return userReference;
    }

    public Reference<User> getAnotherUserReference() {
        return anotherUserReference;
    }

    public ProjectAssignment getProjectAssignment() {
        return projectAssignment;
    }

    public InvoiceItem.Category getInvoiceItemCategory() {
        return invoiceItemCategory;
    }

    public InvoiceItem.Category getAnotherInvoiceItemCategory() {
        return anotherInvoiceItemCategory;
    }

    public EstimateItem.Category getAnotherEstimateItemCategory() {
        return anotherEstimateItemCategory;
    }

    public Reference<ExpenseCategory> getExpenseCategory() {
        return expenseCategory;
    }

    public Reference<Project> getAnotherProjectReference() {
        return anotherProjectReference;
    }
}
