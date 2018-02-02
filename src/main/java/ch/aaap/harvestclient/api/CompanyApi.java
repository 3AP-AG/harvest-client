package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.domain.Company;

public interface CompanyApi {

    /**
     * Retrieve the company for the currently authenticated User.
     * 
     * @return
     */
    Company get();

}
