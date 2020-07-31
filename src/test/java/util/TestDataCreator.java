package util;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.Api;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.ImmutableTimeEntryCreationInfoDuration;
import ch.aaap.harvestclient.domain.reference.GenericReference;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.exception.NotFoundException;

public class TestDataCreator {

    private static final Logger log = LoggerFactory.getLogger(TestDataCreator.class);
    /**
     * we don't want to have collisions with existing objects
     */
    private int id = new Random().nextInt(100_000);

    private Harvest harvest;

    public TestDataCreator(Harvest harvest) {
        this.harvest = harvest;
    }

    /**
     * For each element in testData, check that the object with the given id exists.
     * If not, create a new one and set the new id in testData.
     * 
     * @param testData
     *            a containter for all needed testData
     */
    @SuppressWarnings("ConstantConditions")
    public void getOrCreateAll(TestData testData) {

        Client client = getOrCreate(testData.getClientId(), harvest.clients(),
                () -> createClient("client name"));
        testData.setClientId(client.getId());

        Client anotherClient = getOrCreate(testData.getAnotherClientId(),
                harvest.clients(),
                () -> createClient("another fixClientName"));
        testData.setAnotherClientId(anotherClient.getId());

        User user = getOrCreate(testData.getUserId(), harvest.users(), () -> createUser("first"));
        testData.setUserId(user.getId());

        User anotherUser = getOrCreate(testData.getAnotherUserId(), harvest.users(),
                () -> createUser("another"));
        testData.setAnotherUserId(anotherUser.getId());

        ClientContact clientContact = getOrCreate(testData.getClientContactId(),
                harvest.clientContacts(),
                () -> createClientContact(client, "first client contact"));
        testData.setClientContactId(clientContact.getId());

        ClientContact anotherClientContact = getOrCreate(testData.getAnotherClientContactId(), harvest.clientContacts(),
                () -> createClientContact(client, "second client contact"));
        testData.setAnotherClientContactId(anotherClientContact.getId());

        Project project = getOrCreate(testData.getProjectId(), harvest.projects(), () -> createProject(client));
        testData.setProjectId(project.getId());

        Project anotherProject = getOrCreate(testData.getAnotherProjectId(),
                harvest.projects(),
                () -> createProject(anotherClient));
        testData.setAnotherProjectId(anotherProject.getId());

        Task task = getOrCreate(testData.getTaskId(), harvest.tasks(),
                () -> createTask("fix Task name"));
        testData.setTaskId(task.getId());

        Task anotherTask = getOrCreate(testData.getAnotherTaskId(), harvest.tasks(),
                () -> createTask("another fix Task name"));
        testData.setAnotherTaskId(anotherTask.getId());

        TaskAssignment taskAssignment = getOrCreateNested(testData.getTaskAssignmentId(), harvest.taskAssignments(),
                (p) -> createTaskAssignment(p, task), project);
        testData.setTaskAssignmentId(taskAssignment.getId());

        TimeEntry timeEntry = getOrCreate(testData.getTimeEntryId(),
                harvest.timesheets(),
                () -> createTimeEntry(project, task));
        testData.setTimeEntryId(timeEntry.getId());

        ExpenseCategory expenseCategory = getOrCreate(testData.getExpenseCategoryId(), harvest.expenseCategories(),
                this::createExpenseCategory);
        testData.setExpenseCategoryId(expenseCategory.getId());
    }

    private <T> T getOrCreate(long id, Api.Get<T> api, Supplier<T> creationFunction) {
        T value;
        try {
            value = api.get(GenericReference.of(id));
        } catch (NotFoundException e) {
            value = creationFunction.get();
            log.debug("Created value {}", value);
        }
        return value;
    }

    private <T, C extends Reference<C>> T getOrCreateNested(long id, Api.GetNested<C, T> api,
            Function<C, T> creationFunction, C context) {
        T value;
        try {
            value = api.get(context, GenericReference.of(id));
        } catch (NotFoundException e) {
            value = creationFunction.apply(context);
            log.debug("Created value {}", value);
        }
        return value;
    }

    private ExpenseCategory createExpenseCategory() {
        return harvest.expenseCategories().create(ImmutableExpenseCategory.builder()
                .name("test category" + getId()).build());
    }

    private ClientContact createClientContact(Client client, String firstName) {
        return harvest.clientContacts().create(ImmutableClientContact.builder()
                .client(client)
                .firstName(firstName + getId())
                .build());
    }

    private TimeEntry createTimeEntry(Project project, Task task) {
        return harvest.timesheets().create(ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(task)
                .spentDate(LocalDate.now())
                .hours(2.)
                .build());
    }

    private TaskAssignment createTaskAssignment(Project project, Task task) {
        return harvest.taskAssignments().create(project, ImmutableTaskAssignment.builder()
                .taskReference(task)
                .build());
    }

    private Task createTask(String taskName) {
        return harvest.tasks().create(ImmutableTask.builder()
                .name(taskName + getId())
                .build());
    }

    private Project createProject(Client client) {
        return harvest.projects().create(ImmutableProject.builder()
                .client(client)
                .name("fixProject" + getId())
                .billable(true)
                .billBy(Project.BillingMethod.PROJECT)
                .budgetBy(Project.BudgetMethod.HOURS_PER_PERSON)
                .hourlyRate(21.)
                .build());
    }

    private Client createClient(String fixClientName) {
        return harvest.clients().create(ImmutableClient.builder()
                .name(fixClientName + getId())
                .build());
    }

    private User createUser(String tag) {
        return harvest.users().create(ImmutableUser.builder()
                .firstName("Mario" + tag)
                .lastName("Muster " + getId())
                .email("testFix" + getId() + tag + "@example.com")
                .build());
    }

    private int getId() {
        id++;
        return id;
    }

}
