package ch.aaap.harvestclient.impl.expenseCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpenseCategoriesApi;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.ImmutableExpenseCategory;
import ch.aaap.harvestclient.domain.param.ExpenseCategoryUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableExpenseCategoryUpdateInfo;
import util.TestSetupUtil;

@HarvestTest
class ExpenseCategoriesApiUpdateTest {

    private static final ExpenseCategoriesApi expenseCategorysApi = TestSetupUtil.getAdminAccess().expenseCategories();
    private ExpenseCategory expenseCategory;

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        expenseCategory = ImmutableExpenseCategory.builder()
                .name("test ExpenseCategory for " + testInfo.getDisplayName())
                .build();
        expenseCategory = expenseCategorysApi.create(expenseCategory);
    }

    @AfterEach
    void afterEach() {
        if (expenseCategory != null) {
            expenseCategorysApi.delete(expenseCategory);
            expenseCategory = null;
        }
    }

    @Test
    void changeName() {
        ExpenseCategoryUpdateInfo changes = ImmutableExpenseCategoryUpdateInfo.builder()
                .name("new expenseCategory name" + TestSetupUtil.getTestRunId())
                .build();
        ExpenseCategory updatedExpenseCategory = expenseCategorysApi.update(expenseCategory, changes);

        assertThat(updatedExpenseCategory.getName()).isEqualTo(changes.getName());
    }

    @Test
    void changeAll() {

        ExpenseCategoryUpdateInfo changes = ImmutableExpenseCategoryUpdateInfo.builder()
                .name("expense category 2" + TestSetupUtil.getTestRunId())
                .active(false)
                .unitName("chunk")
                .unitPrice(23.)
                .build();
        ExpenseCategory updatedExpenseCategory = expenseCategorysApi.update(expenseCategory, changes);

        assertThat(updatedExpenseCategory).isEqualToComparingOnlyGivenFields(changes, "name", "active", "unitName",
                "unitPrice");

    }

}
