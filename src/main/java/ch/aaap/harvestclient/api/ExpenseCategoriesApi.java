package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.ExpenseCategoryFilter;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ExpenseCategoryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface ExpenseCategoriesApi extends Api.Simple<ExpenseCategory> {
    /**
     *
     * @param filter
     *            filtering options
     * @return a list of all ExpenseCategories in the account, sorted by creation
     *         date, newest first.
     */
    List<ExpenseCategory> list(ExpenseCategoryFilter filter);

    /**
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all ExpenseCategories in the account, sorted by creation
     *         date, newest first.
     */
    Pagination<ExpenseCategory> list(ExpenseCategoryFilter filter, int page, int perPage);

    /**
     * Return an existing ExpenseCategory.
     *
     * @param expenseCategoriesReference
     *            a reference to an existing ExpenseCategory
     * @return the full ExpenseCategory object
     */
    @Override
    ExpenseCategory get(Reference<ExpenseCategory> expenseCategoriesReference);

    /**
     * Create a new ExpenseCategory. Example:
     * 
     * <pre>
     * ExpenseCategory expenseCategories = harvest.expenseCategories().create(ImmutableExpenseCategory.builder()
     *         .name("category name")
     *         .build());
     * </pre>
     *
     * @param creationInfo
     *            creation information
     * @return the created ExpenseCategory
     */
    @Override
    ExpenseCategory create(ExpenseCategory creationInfo);

    /**
     * Updates the specific expenseCategories by setting the values of the
     * parameters passed. Any parameters not provided will be left unchanged
     *
     * @param expenseCategoriesReference
     *            An existing ExpenseCategory to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated ExpenseCategory
     */
    ExpenseCategory update(Reference<ExpenseCategory> expenseCategoriesReference, ExpenseCategoryUpdateInfo toChange);

    /**
     * Delete an existing ExpenseCategory.
     * 
     * @param expenseCategoriesReference
     *            a reference to the ExpenseCategory to be deleted
     */
    @Override
    void delete(Reference<ExpenseCategory> expenseCategoriesReference);

}
