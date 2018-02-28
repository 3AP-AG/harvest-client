package ch.aaap.harvestclient.impl.expenseCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpenseCategoriesApi;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.ImmutableExpenseCategory;
import util.TestSetupUtil;

@HarvestTest
class ExpenseCategoriesApiCreateTest {

    private static final ExpenseCategoriesApi expenseCategorysApi = TestSetupUtil.getAdminAccess().expenseCategories();
    private ExpenseCategory expenseCategory;

    @AfterEach
    void afterEach() {
        if (expenseCategory != null) {
            expenseCategorysApi.delete(expenseCategory);
            expenseCategory = null;
        }
    }

    @Test
    void create() {

        String name = "test ExpenseCategory";
        ExpenseCategory creationInfo = ImmutableExpenseCategory.builder().name(name).build();
        expenseCategory = expenseCategorysApi.create(creationInfo);

        assertThat(expenseCategory.getName()).isEqualTo(name);
    }

    @Test
    void createAllOptions() {

        ExpenseCategory creationInfo = ImmutableExpenseCategory.builder()
                .name("expense category 2")
                .active(false)
                .unitName("chunk")
                .unitPrice(23.)
                .build();

        expenseCategory = expenseCategorysApi.create(creationInfo);

        assertThat(expenseCategory).isEqualToIgnoringNullFields(creationInfo);
    }

}
