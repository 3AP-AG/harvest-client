package ch.aaap.harvestclient.impl.expense;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.exception.RequestProcessingException;
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
                .build();
        expense = expensesApi.create(creationInfo);

        assertThat(expense).usingComparatorForType(Comparator.comparing(Reference::getId), Reference.class)
                .isEqualToIgnoringNullFields(creationInfo);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1x1.png",
            "png-transparent.png",
            "gif.gif",
            "gif-transparent.gif",
            "jpg.jpg",
            "pdf.pdf"
    })

    void attachReceipt(String filename) throws IOException {

        try (InputStream in = getClass().getResourceAsStream("/minimal_files/" + filename)) {

            Expense creationInfo = ImmutableExpense.builder()
                    .project(project)
                    .expenseCategory(expenseCategory)
                    .spentDate(LocalDate.now())
                    .user(user)
                    .totalCost(22.)
                    .build();
            expense = expensesApi.create(creationInfo);

            expense = expensesApi.attachReceipt(expense, in, filename);

            Receipt receipt = expense.getReceipt();
            assertThat(receipt).isNotNull();
            assertThat(receipt.getUrl()).isNotEmpty();
            // harvest is converting the filename for some reason
            assertThat(receipt.getFilename()).isEqualTo(filename.replace("-", "_"));

        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "mp3.mp3"

    })
    void attachReceiptException(String filename) throws IOException {

        try (InputStream in = getClass().getResourceAsStream("/minimal_files/" + filename)) {

            Expense creationInfo = ImmutableExpense.builder()
                    .project(project)
                    .expenseCategory(expenseCategory)
                    .spentDate(LocalDate.now())
                    .user(user)
                    .totalCost(22.)
                    .build();
            expense = expensesApi.create(creationInfo);

            assertThrows(RequestProcessingException.class,
                    () -> expense = expensesApi.attachReceipt(expense, in, filename));

        }
    }

    @Test
    void deleteReceipt() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/minimal_files/gif.gif")) {

            Expense creationInfo = ImmutableExpense.builder()
                    .project(project)
                    .expenseCategory(expenseCategory)
                    .spentDate(LocalDate.now())
                    .user(user)
                    .totalCost(22.)
                    .build();
            expense = expensesApi.create(creationInfo);

            expense = expensesApi.attachReceipt(expense, in, "gif.gif");

            expense = expensesApi.removeReceipt(expense);

            assertThat(expense.getReceipt()).isNull();

        }

    }

}
