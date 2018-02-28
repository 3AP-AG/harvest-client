package ch.aaap.harvestclient.impl.expense;

import java.time.LocalDate;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ExpensesApiCreateTest {

    private static final ExpensesApi expensesApi = TestSetupUtil.getAdminAccess().expenses();
    private final Reference<Project> project = ExistingData.getInstance().getProjectReference();
    private final Reference<User> user = ExistingData.getInstance().getUserReference();
    private final Reference<ExpenseCategory> expenseCategory = ExistingData.getInstance().getExpenseCategory();
    private Expense expense;

    @AfterEach
    void afterEach() {
        if (expense != null) {
            expensesApi.delete(expense);
            expense = null;
        }
    }

    @Test
    void create() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        assertThat(expense).usingComparatorForType(Comparator.comparing(Reference::getId), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createAllOptions() {

        String name = "test Expense";
        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .notes("test notes")
                .billable(false)
                // TODO need multipart
                // .receipt()
                .build();
        expense = expensesApi.create(creationInfo);

        assertThat(expense).usingComparatorForType(Comparator.comparing(Reference::getId), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);

    }

}
