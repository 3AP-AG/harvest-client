package ch.aaap.harvestclient.impl.expense;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.api.filter.ExpenseFilter;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class ExpensesApiListTest {

    private static final ExpensesApi expensesApi = TestSetupUtil.getAdminAccess().expenses();
    private Expense expense;
    private final Reference<Project> project = ExistingData.getInstance().getProjectReference();
    private final Reference<User> user = ExistingData.getInstance().getUserReference();
    private final Reference<ExpenseCategory> expenseCategory = ExistingData.getInstance().getExpenseCategory();

    @AfterEach
    void afterEach() {
        if (expense != null) {
            expensesApi.delete(expense);
            expense = null;
        }
    }

    @Test
    void list() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        List<Expense> expenses = expensesApi.list(new ExpenseFilter());

        assertThat(expenses).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
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
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        Expense creationInfo = ImmutableExpense.builder()
                .project(project)
                .expenseCategory(expenseCategory)
                .spentDate(LocalDate.now())
                .user(user)
                .totalCost(22.)
                .build();
        expense = expensesApi.create(creationInfo);

        ExpenseFilter filter = new ExpenseFilter();
        filter.setUpdatedSince(creationTime);

        List<Expense> expenses = expensesApi.list(filter);

        assertThat(expenses).usingFieldByFieldElementComparator().containsExactly(expense);

    }

}
