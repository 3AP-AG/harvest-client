package ch.aaap.harvestclient.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.api.CompanyApi;
import ch.aaap.harvestclient.domain.Company;
import ch.aaap.harvestclient.service.CompanyService;
import retrofit2.Call;

public class CompanyApiImpl implements CompanyApi {

    private static final Logger log = LoggerFactory.getLogger(CompanyApiImpl.class);
    private final CompanyService service;

    public CompanyApiImpl(CompanyService companyService) {
        this.service = companyService;
    }

    @Override
    public Company get() {
        Call<Company> call = service.get();
        Company company = ExceptionHandler.callOrThrow(call);

        log.debug("Got Company info {} ", company);
        return company;
    }
}
