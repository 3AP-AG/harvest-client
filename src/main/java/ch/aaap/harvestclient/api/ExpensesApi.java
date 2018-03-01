package ch.aaap.harvestclient.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ch.aaap.harvestclient.api.filter.ExpenseFilter;
import ch.aaap.harvestclient.domain.Expense;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ExpenseUpdateInfo;
import ch.aaap.harvestclient.domain.param.ImmutableExpenseUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(value = Api.Role.NONE, onlySelf = true)
public interface ExpensesApi extends Api.Simple<Expense> {
    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all Expenses in the account, sorted by creation date,
     *         newest first. If no admin rights, return only the user expenses.
     */
    List<Expense> list(ExpenseFilter filter);

    /**
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all Expenses in the account, sorted by creation date,
     *         newest first. If no admin rights, return only the user expenses.
     */
    Pagination<Expense> list(ExpenseFilter filter, int page, int perPage);

    /**
     * Return an existing Expense.
     *
     * @param expenseReference
     *            a reference to an existing Expense
     * @return the full Expense object
     */
    @Override
    Expense get(Reference<Expense> expenseReference);

    /**
     * Create a new Expense. Example:
     * 
     * <pre>
     * Expense expense = harvest.expenses().create(ImmutableExpense.builder()
     *         .name("expense name")
     *         .build());
     * </pre>
     *
     * @param creationInfo
     *            creation information
     * @return the created Expense
     */
    @Override
    Expense create(Expense creationInfo);

    /**
     * Updates the specific expense by setting the values of the parameters passed.
     * Any parameters not provided will be left unchanged
     *
     * @param expenseReference
     *            An existing Expense to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated Expense
     */
    Expense update(Reference<Expense> expenseReference, ExpenseUpdateInfo toChange);

    /**
     * Attach a file as a receipt for an Expense
     *
     * @param expenseReference
     *            An existing Expense to be updated
     * @param inputStream
     *            a receipt inputstream (allowed filetypes: png, gif, pdf, jpeg,
     *            jpg). Calling code is responsible for closing the stream
     * @param fileName
     *            a filename for Harvest (optional)
     * @return the updated Expense
     */
    Expense attachReceipt(Reference<Expense> expenseReference, InputStream inputStream, String fileName)
            throws IOException;

    /**
     * Attach a file as a receipt for an Expense
     * 
     * @param expenseReference
     *            An existing Expense to be updated
     * @param file
     *            a receipt file (allowed extensions: png, gif, pdf, jpeg, jpg)
     * @return the updated Expense
     */
    Expense attachReceipt(Reference<Expense> expenseReference, File file);

    /**
     * Remove a receipt from an Expense, if one is currently present. Note that
     * attachReceipt can be used even if a receipt is already set, no need to call
     * this first
     * 
     * @param expenseReference
     *            the expense from which to remove the receipt
     * @return the updated Expense
     */
    default Expense removeReceipt(Reference<Expense> expenseReference) {
        return update(expenseReference, ImmutableExpenseUpdateInfo.builder()
                .deleteReceipt(true)
                .build());
    }

    /**
     * Delete an existing Expense. Only possible if no time entries are associated
     * with it
     * 
     * @param expenseReference
     *            a reference to the Expense to be deleted
     */
    @Override
    void delete(Reference<Expense> expenseReference);

}
