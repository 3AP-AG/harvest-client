package ch.aaap.harvestclient.service;

import ch.aaap.harvestclient.domain.Company;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CompanyService {

    @GET("company")
    Call<Company> get();

}
