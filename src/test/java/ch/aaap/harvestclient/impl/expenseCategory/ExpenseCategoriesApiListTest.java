package ch.aaap.harvestclient.impl.expenseCategory;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpenseCategoriesApi;
import ch.aaap.harvestclient.api.filter.ExpenseCategoryFilter;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.ImmutableExpenseCategory;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import util.TestSetupUtil;

@HarvestTest
class ExpenseCategoriesApiListTest {

    private static final ExpenseCategoriesApi expenseCategoriesApi = TestSetupUtil.getAdminAccess().expenseCategories();
    private ExpenseCategory expenseCategory;

    @AfterEach
    void afterEach() {
        if (expenseCategory != null) {
            expenseCategoriesApi.delete(expenseCategory);
            expenseCategory = null;
        }
    }

    @Test
    void list() {

        List<ExpenseCategory> expenseCategorys = expenseCategoriesApi.list(new ExpenseCategoryFilter());

        assertThat(expenseCategorys).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Pagination<ExpenseCategory> pagination = expenseCategoriesApi.list(new ExpenseCategoryFilter(), 1, 1);

        List<ExpenseCategory> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByActive() {

        ExpenseCategory creationInfo = ImmutableExpenseCategory.builder()
                .name("inactive test ExpenseCategory")
                .active(false)
                .build();
        expenseCategory = expenseCategoriesApi.create(creationInfo);

        ExpenseCategoryFilter filter = new ExpenseCategoryFilter();
        filter.setActive(false);

        List<ExpenseCategory> expenseCategorys = expenseCategoriesApi.list(filter);

        assertThat(expenseCategorys).usingFieldByFieldElementComparator().containsExactly(expenseCategory);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        ExpenseCategory creationInfo = ImmutableExpenseCategory.builder()
                .name("newly created test ExpenseCategory")
                .build();
        expenseCategory = expenseCategoriesApi.create(creationInfo);

        ExpenseCategoryFilter filter = new ExpenseCategoryFilter();
        filter.setUpdatedSince(creationTime);

        List<ExpenseCategory> expenseCategorys = expenseCategoriesApi.list(filter);

        assertThat(expenseCategorys).usingFieldByFieldElementComparator().containsExactly(expenseCategory);

    }

}
