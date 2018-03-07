package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.domain.Company;

/**
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/company-api/company/company/">
 *      Company API on Harvest</a>
 */
@Api.Permission(Api.Role.NONE)
public interface CompanyApi {

    /**
     * @return Retrieve the company for the currently authenticated User.
     */
    Company get();

}
