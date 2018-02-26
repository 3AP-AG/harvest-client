package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ExistingData {

    public EstimateItem.Category getAnotherEstimateItemCategory() {
        return anotherEstimateItemCategory;
    }

    /**
     * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
     */
    private static class LazyHolder {
        static final ExistingData INSTANCE = new ExistingData(TestSetupUtil.getAdminAccess());
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
    private final Reference<User> userReference;

    private final Reference<TimeEntry> timeEntryReference;
    private final EstimateItem.Category estimateItemCategory;
    private final EstimateItem.Category anotherEstimateItemCategory;

    private final InvoiceItem.Category invoiceItemCategory;
    private final InvoiceItem.Category anotherInvoiceItemCategory;

    private final ProjectAssignment projectAssignment;

    private ExistingData(Harvest harvest) {
        try {
            log.debug("Getting Existing Data for tests");

            TestData data = loadFromFile();
            if (checkValid) {
                new TestDataCreator(TestSetupUtil.getAdminAccess()).getOrCreateAll(data);
            }
            log.info("TestData is {}", data);

            taskReference = new GenericReference<>(data.getTaskId());
            anotherTaskReference = new GenericReference<>(data.getAnotherTaskId());

            clientReference = new GenericReference<>(data.getClientId());
            anotherClientReference = new GenericReference<>(data.getAnotherClientId());

            projectReference = new GenericReference<>(data.getProjectId());
            userReference = new GenericReference<>(data.getUserId());

            timeEntryReference = new GenericReference<>(data.getTimeEntryId());

            // default categories
            estimateItemCategory = ImmutableEstimateItem.Category.builder().name("Product").build();
            anotherEstimateItemCategory = ImmutableEstimateItem.Category.builder().name("Service").build();

            // default categories
            invoiceItemCategory = ImmutableInvoiceItem.Category.builder().name("Product").build();
            anotherInvoiceItemCategory = ImmutableInvoiceItem.Category.builder().name("Service").build();

            // TODO this is manual, need UserProjectAssignmentsapi
            projectAssignment = harvest.projectAssignments().list(userReference).get(0);

        } catch (Throwable t) {
            log.error("", t);
            throw t;
        }
    }

    private TestData loadFromFile() {
        // TODO implement file loading
        TestData data = new TestData();
        data.setTimeEntryId(750333887);
        data.setUserId(2040413);

        data.setClientId(6537932);
        data.setAnotherClientId(6537933);

        data.setClientContactId(5054209);
        data.setAnotherClientContactId(5054210);
        data.setProjectId(16561328);
        data.setAnotherProjectId(16561329);
        data.setTaskId(9316038);
        data.setAnotherTaskId(9316039);
        data.setTaskAssignmentId(179269102);
        return data;
    }

    public static ExistingData getInstance() {
        return LazyHolder.INSTANCE;
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

    public ProjectAssignment getProjectAssignment() {
        return projectAssignment;
    }

    public InvoiceItem.Category getInvoiceItemCategory() {
        return invoiceItemCategory;
    }

    public InvoiceItem.Category getAnotherInvoiceItemCategory() {
        return anotherInvoiceItemCategory;
    }
}
