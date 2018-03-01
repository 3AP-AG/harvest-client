package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.ExpenseCategoryUpdateInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface ExpenseCategoryService {

    String basePath = "expense_categories";
    String id = "expenseCategoryId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<ExpenseCategory> get(@Path(id) long expenseCategoryId);

    @POST(basePath)
    Call<ExpenseCategory> create(@Body ExpenseCategory creationInfo);

    @PATCH(path)
    Call<ExpenseCategory> update(@Path(id) long expenseCategoryId, @Body ExpenseCategoryUpdateInfo updateInfo);

    @DELETE(path)
    Call<Void> delete(@Path(id) long expenseCategoryId);
}
