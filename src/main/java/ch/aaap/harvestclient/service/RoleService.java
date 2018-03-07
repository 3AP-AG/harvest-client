package ch.aaap.harvestclient.service;

import ch.aaap.harvestclient.domain.Role;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;
import ch.aaap.harvestclient.domain.param.RoleInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface RoleService {

    @GET("roles")
    Call<PaginatedList> list(@Query("page") int page, @Query("per_page") int perPage);

    @GET("roles/{roleId}")
    Call<Role> get(@Path("roleId") long roleId);

    @POST("roles")
    Call<Role> create(@Body RoleInfo roleInfo);

    @PATCH("roles/{roleId}")
    Call<Role> update(@Path("roleId") long roleId, @Body RoleInfo toChange);

    @DELETE("roles/{roleId}")
    Call<Void> delete(@Path("roleId") long roleId);
}
