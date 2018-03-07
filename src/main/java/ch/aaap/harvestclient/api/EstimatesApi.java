package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.EstimateFilter;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.EstimateItemUpdateInfo;
import ch.aaap.harvestclient.domain.param.EstimateUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/estimates-api/estimates/estimates/">
 *      Estimates API on Harvest</a>
 */
@Api.Permission(Api.Role.ADMIN)
public interface EstimatesApi extends Api.Simple<Estimate> {

    /**
     * Return a list of estimates, sorted by issue date, newest first. Use the
     * filter object to filter the list.
     *
     * @param filter
     *            filtering options
     * @return a (filtered) list of Estimates
     */
    List<Estimate> list(EstimateFilter filter);

    /**
     * Return a list of estimates, sorted by issue date, newest first. Use the
     * filter object to filter the list. Page and perPage allow controlling how many
     * results to return.
     * 
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of Estimates
     */
    Pagination<Estimate> list(EstimateFilter filter, int page, int perPage);

    /**
     * @param estimateReference
     *            a reference to an existing Estimate
     * @return Return a full Estimate object
     */
    @Override
    Estimate get(Reference<Estimate> estimateReference);

    /**
     * Create a new Estimate according to given creation information.
     *
     * @param estimateCreationInfo
     *            the creation options
     * @return the newly created Estimate
     */
    @Override
    Estimate create(Estimate estimateCreationInfo);

    /**
     * Create a new estimate line item and add it to the given estimate
     * 
     * @param estimateReference
     *            the estimate to add the item to
     * @param creationInfo
     *            the line item to be created
     * @return the updated Estimate
     */
    Estimate addLineItem(Reference<Estimate> estimateReference, EstimateItem creationInfo);

    /**
     * Update an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateItemReference
     *            a reference to the line item
     * @param updateInfo
     *            the line item to be updated
     * @return the updated Estimate
     */
    Estimate updateLineItem(Reference<Estimate> estimateReference, Reference<EstimateItem> estimateItemReference,
            EstimateItemUpdateInfo updateInfo);

    /**
     * Delete an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateItemReference
     *            the line item to be deleted
     * @return the updated Estimate
     */
    Estimate deleteLineItem(Reference<Estimate> estimateReference, Reference<EstimateItem> estimateItemReference);

    /**
     * Create new estimate line items and add it to the given estimate
     *
     * @param estimateReference
     *            the estimate to add the item to
     * @param creationInfoList
     *            a list of items to be created
     * @return the updated Estimate
     */
    Estimate addLineItems(Reference<Estimate> estimateReference, List<EstimateItem> creationInfoList);

    /**
     * Update an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateItemReferenceList
     *            a list of the item references to be updated, in the same order as
     *            updateInfoList
     * @param updateInfoList
     *            a list of the line items to be updated
     * @return the updated Estimate
     */
    Estimate updateLineItems(Reference<Estimate> estimateReference,
            List<? extends Reference<EstimateItem>> estimateItemReferenceList,
            List<EstimateItemUpdateInfo> updateInfoList);

    /**
     * Delete an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateItemReferenceList
     *            a list of the line items to be deleted
     * @return the updated Estimate
     */
    Estimate deleteLineItems(Reference<Estimate> estimateReference,
            List<? extends Reference<EstimateItem>> estimateItemReferenceList);

    /**
     * Updates an existing Estimate with the properties set in EstimateUpdateInfo.
     * Be careful: setting a list of EstimateItem will add them to the existing
     * ones, not replace them. See {@link #deleteLineItems(Reference, List)} to
     * delete existing items
     *
     * @param estimateReference
     *            the existing estimate to be updated
     * @param toChange
     *            the properties to be updated
     * @return the updated Estimate
     */
    Estimate update(Reference<Estimate> estimateReference, EstimateUpdateInfo toChange);

    /**
     * Delete an existing Estimate.
     *
     * @param estimateReference
     *            a reference to an existing Estimate to be deleted
     */
    @Override
    void delete(Reference<Estimate> estimateReference);
}
