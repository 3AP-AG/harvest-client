package ch.aaap.harvestclient.service;

import java.util.Map;

import ch.aaap.harvestclient.domain.Expense;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.ExpenseUpdateInfo;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ExpenseService {

    String basePath = "expenses";
    String id = "expenseId";
    String path = basePath + "/{" + id + "}";

    @GET(basePath)
    Call<PaginatedList> list(@QueryMap Map<String, Object> options);

    @GET(path)
    Call<Expense> get(@Path(id) long expenseId);

    @POST(basePath)
    Call<Expense> create(@Body Expense creationInfo);

    @PATCH(path)
    Call<Expense> update(@Path(id) long expenseId, @Body ExpenseUpdateInfo updateInfo);

    @Multipart
    @PATCH(path)
    Call<Expense> attachFile(@Path(id) long expenseId, @Part MultipartBody.Part receipt);

    @DELETE(path)
    Call<Void> delete(@Path(id) long expenseId);
}
