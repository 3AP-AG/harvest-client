package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ExpenseCategoriesApi;
import ch.aaap.harvestclient.api.filter.ExpenseCategoryFilter;
import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ExpenseCategoryUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ExpenseCategoryService;
import retrofit2.Call;

public class ExpenseCategoriesApiImpl implements ExpenseCategoriesApi {

    private static final Logger log = LoggerFactory.getLogger(ExpenseCategoriesApiImpl.class);
    private final ExpenseCategoryService service;

    public ExpenseCategoriesApiImpl(ExpenseCategoryService service) {
        this.service = service;
    }

    @Override
    public List<ExpenseCategory> list(ExpenseCategoryFilter filter) {
        return Common.collect((page, perPage) -> list(filter, page, perPage));
    }

    @Override
    public Pagination<ExpenseCategory> list(ExpenseCategoryFilter filter, int page, int perPage) {
        log.debug("Getting page {} of ExpenseCategory list", page);
        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));
        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getExpenseCategories());
    }

    @Override
    public ExpenseCategory get(Reference<ExpenseCategory> expenseCategoryReference) {
        Call<ExpenseCategory> call = service.get(expenseCategoryReference.getId());
        ExpenseCategory expenseCategory = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", expenseCategory);
        return expenseCategory;
    }

    @Override
    public ExpenseCategory create(ExpenseCategory creationInfo) {
        Call<ExpenseCategory> call = service.create(creationInfo);
        ExpenseCategory expenseCategory = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", expenseCategory);
        return expenseCategory;
    }

    @Override
    public ExpenseCategory update(Reference<ExpenseCategory> expenseCategoryReference,
            ExpenseCategoryUpdateInfo toChange) {
        log.debug("Updating {} with {}", expenseCategoryReference, toChange);
        Call<ExpenseCategory> call = service.update(expenseCategoryReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<ExpenseCategory> expenseCategoryReference) {
        log.debug("Deleting {}", expenseCategoryReference);
        Call<Void> call = service.delete(expenseCategoryReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
