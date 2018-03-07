package ch.aaap.harvestclient.impl.expense;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.api.filter.ExpenseFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ExpensesApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final ExpensesApi expensesApi = harvest.expenses();
    private static final Reference<Project> existingProject = ExistingData.getInstance().getProjectReference();
    private static final Reference<User> existingUser = ExistingData.getInstance().getUserReference();
    private static final Reference<ExpenseCategory> expenseCategory = ExistingData.getInstance().getExpenseCategory();
    private static Expense fixExpense;
    private static Expense expense;
    private static User user;
    private static Client client;
    private static Project project;
    private Invoice invoice;

    @BeforeAll
    static void beforeAll(TestInfo testInfo) {
        // create a base expense to make sure filtering works
        Expense creationInfo = ImmutableExpense.builder()
                .project(existingProject)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(existingUser)
                .totalCost(22.)
                .build();
        fixExpense = expensesApi.create(creationInfo);

        client = harvest.clients().create(ImmutableClient.builder()
                .name("test client for " + testInfo.getDisplayName())
                .build());

        project = harvest.projects().create(ImmutableProject.builder()
                .name("project for " + testInfo.getDisplayName())
                .client(client)
                .billable(true)
                .billBy(Project.BillingMethod.PROJECT)
                .budgetBy(Project.BudgetMethod.HOURS_PER_PROJECT)
                .build());

        user = harvest.users().create(TestSetupUtil.getRandomUserCreationInfo());
        harvest.userAssignments().create(project, ImmutableUserAssignment.builder().user(user).build());

    }

    @AfterAll
    static void afterAll() {
        if (fixExpense != null) {
            expensesApi.delete(fixExpense);
        }
        if (project != null) {
            harvest.projects().delete(project);
        }
        if (user != null) {
            harvest.users().delete(user);
        }
        if (client != null) {
            harvest.clients().delete(client);
        }
    }

    @AfterEach
    void afterEach() {
        if (expense != null) {
            expensesApi.delete(expense);
            expense = null;
        }

        if (invoice != null) {
            harvest.invoices().delete(invoice);
        }
    }

    @Test
    void list() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(existingProject)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(existingUser)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        List<Expense> expenses = expensesApi.list(new ExpenseFilter());

        assertThat(expenses).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(existingProject)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(existingUser)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);
        expense = expensesApi.create(creationInfo);

        Pagination<Expense> pagination = expensesApi.list(new ExpenseFilter(), 1, 1);

        List<Expense> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByUpdatedSince() throws InterruptedException {

        Expense creationInfo = ImmutableExpense.builder()
                .project(existingProject)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(existingUser)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        if (fixExpense.getUpdatedAt().equals(expense.getUpdatedAt())) {
            // we need at least one second difference
            Thread.sleep(1_000);
            // do an update to change the updatedAt field
            expensesApi.update(expense, ImmutableExpenseUpdateInfo.builder()
                    .totalCost(11.)
                    .build());
            // refresh expense
            expense = expensesApi.get(expense);
        }

        ExpenseFilter filter = new ExpenseFilter();
        filter.setUpdatedSince(expense.getUpdatedAt().minusSeconds(1));

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);

    }

    @Test
    void listByUser() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setUserReference(user);

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);
    }

    @Test
    void listByClient() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setClientReference(client);

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);
    }

    @Test
    void listByProject() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setProjectReference(project);

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);
    }

    @Test
    void listByFrom() {

        LocalDate creationDate = LocalDate.of(2001, 1, 4);

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(creationDate)
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setFrom(creationDate.plusDays(1));

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().doesNotContain(expense);
    }

    @Test
    void listByTo() {

        LocalDate creationDate = LocalDate.of(2001, 1, 4);

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(creationDate)
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setTo(creationDate.plusDays(1));

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);
    }

    @Test
    void listByBilled() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setBilled(true);

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).isEmpty();

        // mark the expense as billed

        invoice = harvest.invoices().createFrom(ImmutableInvoiceImportInfo.builder()
                .client(project.getClient())
                .lineItemsImport(ImmutableInvoiceItemImport.builder()
                        .addProject(project)
                        .expenseImport(ImmutableInvoiceExpenseImport.builder()
                                .summaryType(InvoiceExpenseImport.SummaryType.PROJECT)
                                .build())
                        .build())
                .build());

        expenses = expensesApi.list(filter);

        expense = expensesApi.get(expense);
        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);

    }
}
