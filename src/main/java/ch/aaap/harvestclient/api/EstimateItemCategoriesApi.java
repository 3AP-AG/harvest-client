package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import ch.aaap.harvestclient.domain.EstimateItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(Api.Role.ADMIN)
public interface EstimateItemCategoriesApi extends Api.Simple<EstimateItem.Category> {

    /**
     *
     * @param updatedSince
     *            if set, only return categories updated since the given timestamp
     * @return a list of all EstimateItemCategories in the account, sorted by
     *         creation date, newest first.
     */
    List<EstimateItem.Category> list(@Nullable Instant updatedSince);

    /**
     * Return a list of estimateItemCategories, sorted by creation date, newest
     * first. Use the filter object to filter the list. Page and perPage allow
     * controlling how many results to return.
     *
     * @param updatedSince
     *            if set, only return categories updated since the given timestamp
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of EstimateItemCategory
     */
    Pagination<EstimateItem.Category> list(@Nullable Instant updatedSince, int page, int perPage);

    /**
     * Return an existing EstimateItemCategory.
     *
     * @param estimateItemCategoryReference
     *            a reference to an existing EstimateItemCategory
     * @return the full EstimateItemCategory object
     */
    @Override
    EstimateItem.Category get(Reference<EstimateItem.Category> estimateItemCategoryReference);

    /**
     * Create a new EstimateItemCategory
     *
     * @param creationInfo
     *            creation information
     * @return the created EstimateItemCategory
     */
    @Override
    EstimateItem.Category create(EstimateItem.Category creationInfo);

    /**
     * Updates the specific estimateItemCategory by setting the values of the
     * parameters passed. Any parameters not provided will be left unchanged
     *
     * @param estimateItemCategoryReference
     *            An existing EstimateItemCategory to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated EstimateItemCategory
     */
    EstimateItem.Category update(Reference<EstimateItem.Category> estimateItemCategoryReference,
            EstimateItem.Category toChange);

    /**
     * Delete an existing EstimateItemCategory.
     *
     * @param estimateItemCategoryReference
     *            a reference to the EstimateItemCategory to be deleted
     */
    @Override
    void delete(Reference<EstimateItem.Category> estimateItemCategoryReference);
}
