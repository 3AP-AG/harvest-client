package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.domain.Company;

public interface CompanyApi {

    /**
     * @return Retrieve the company for the currently authenticated User.
     */
    Company get();

}
