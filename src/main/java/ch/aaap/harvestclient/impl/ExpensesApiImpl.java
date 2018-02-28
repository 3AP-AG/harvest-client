package ch.aaap.harvestclient.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.ExpensesApi;
import ch.aaap.harvestclient.api.filter.ExpenseFilter;
import ch.aaap.harvestclient.domain.Expense;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.ExpenseUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.service.ExpenseService;
import retrofit2.Call;

public class ExpensesApiImpl implements ExpensesApi {

    private static final Logger log = LoggerFactory.getLogger(ExpensesApiImpl.class);
    private final ExpenseService service;

    public ExpensesApiImpl(ExpenseService service) {
        this.service = service;
    }

    @Override
    public List<Expense> list(ExpenseFilter filter) {
        return Common.collect((page, perPage) -> list(filter, page, perPage));
    }

    @Override
    public Pagination<Expense> list(ExpenseFilter filter, int page, int perPage) {
        log.debug("Getting page {} of Expense list", page);
        Call<PaginatedList> call = service.list(filter.toMap(page, perPage));
        PaginatedList pagination = ExceptionHandler.callOrThrow(call);
        return Pagination.of(pagination, pagination.getExpenses());
    }

    @Override
    public Expense get(Reference<Expense> expenseReference) {
        Call<Expense> call = service.get(expenseReference.getId());
        Expense expense = ExceptionHandler.callOrThrow(call);
        log.debug("Got {}", expense);
        return expense;
    }

    @Override
    public Expense create(Expense creationInfo) {
        Call<Expense> call = service.create(creationInfo);
        Expense expense = ExceptionHandler.callOrThrow(call);
        log.debug("Created {}", expense);
        return expense;
    }

    @Override
    public Expense update(Reference<Expense> expenseReference, ExpenseUpdateInfo toChange) {
        log.debug("Updating {} with {}", expenseReference, toChange);
        Call<Expense> call = service.update(expenseReference.getId(), toChange);
        return ExceptionHandler.callOrThrow(call);
    }

    @Override
    public void delete(Reference<Expense> expenseReference) {
        log.debug("Deleting {}", expenseReference);
        Call<Void> call = service.delete(expenseReference.getId());
        ExceptionHandler.callOrThrow(call);
    }

}
