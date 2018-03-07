package ch.aaap.harvestclient.impl.expense;

import java.time.LocalDate;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.ExpenseUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableExpenseUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ExpensesApiUpdateTest {

    private static final ExpensesApi expensesApi = TestSetupUtil.getAdminAccess().expenses();
    private final Reference<Project> project = ExistingData.getInstance().getProjectReference();
    private final Reference<User> user = ExistingData.getInstance().getUserReference();
    private final Reference<ExpenseCategory> expenseCategory = ExistingData.getInstance().getExpenseCategory();
    private Expense expense;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        expense = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(expense);
    }

    @AfterEach
    void afterEach() {
        if (expense != null) {
            expensesApi.delete(expense);
            expense = null;
        }
    }

    @Test
    void changeAll() {

        String name = "test Expense";
        boolean billableByDefault = false;
        double defaultHourlyRate = 220.2;
        boolean defaultAddToFutureProject = true;
        boolean active = false;

        ExpenseUpdateInfo changes = ImmutableExpenseUpdateInfo.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .totalCost(55.)
                .spentDate(LocalDate.of(2001, 1, 1))
                .build();
        Expense updatedExpense = expensesApi.update(expense, changes);

        assertThat(updatedExpense).usingComparatorForType(Comparator.comparing(Reference::getId), Reference.class)
                .isEqualToComparingOnlyGivenFields(changes, "project", "expenseCategory", "totalCost", "spentDate");

    }

}
