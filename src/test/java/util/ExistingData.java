package util;

import java.io.*;

import org.assertj.core.util.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

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
            new TestDataCreator(harvest).getOrCreateAll(data);
            log.info("TestData is {}", data);
            saveToFile(accountNumber, data);

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

    private void saveToFile(int accountNumber, TestData data) {
        Gson gson = new Gson();
        File testDataFile = findTestDataFile(accountNumber);
        try (FileWriter writer = new FileWriter(testDataFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            log.error("Problem writing test data file", e);
        }

    }

    private TestData loadFromFile(int accountNumber) {

        File testDataFile = findTestDataFile(accountNumber);

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(testDataFile)) {
            log.debug("Loading data for account {}", accountNumber);
            TestData parsedData = gson.fromJson(reader, TestData.class);
            if (parsedData == null) {
                parsedData = new TestData();
            }
            return parsedData;
        } catch (FileNotFoundException e) {
            log.info("test data file not present");
            return new TestData();
        } catch (IOException e) {
            log.error("Problem reading test file", e);
            return new TestData();
        }
    }

    private File findTestDataFile(int accountNumber) {
        String tmpFolder = Files.temporaryFolderPath();
        File testDataFile = new File(String.format("%sharvest-client-e2etest%s.json", tmpFolder, accountNumber));
        log.info("Using testdata file at {}", testDataFile.toString());
        return testDataFile;
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
