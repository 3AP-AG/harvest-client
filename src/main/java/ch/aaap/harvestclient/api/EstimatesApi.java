package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.EstimateFilter;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateLineItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.EstimateUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface EstimatesApi {

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
    Estimate get(Reference<Estimate> estimateReference);

    /**
     * Create a new Estimate according to given creation information.
     *
     * @param estimateCreationInfo
     *            the creation options
     * @return the newly created Estimate
     */
    Estimate create(Estimate estimateCreationInfo);

    /**
     * Create a new estimate line item and add it to the given estimate
     * 
     * @param estimateReference
     *            the estimate to add the item to
     * @param estimateLineItem
     *            the line item to be created
     * @return the updated Estimate
     */
    Estimate addLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem);

    /**
     * Update an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateLineItem
     *            the line item to be updated
     * @return the updated Estimate
     */
    Estimate updateLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem);

    /**
     * Delete an existing line item
     *
     * @param estimateReference
     *            the estimate that contains the item
     * @param estimateLineItem
     *            the line item to be deleted
     * @return the updated Estimate
     */
    Estimate deleteLineItem(Reference<Estimate> estimateReference, EstimateLineItem estimateLineItem);

    /**
     * Updates an existing Estimate with the properties set in EstimateUpdateInfo
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
    void delete(Reference<Estimate> estimateReference);
}
