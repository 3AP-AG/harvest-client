package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import javax.annotation.Nullable;

import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/invoices-api/invoices/invoices/">
 *      Invoices API on Harvest</a>
 */
@Api.Permission(Api.Role.PROJECT_MANAGER)
public interface InvoiceItemCategoriesApi extends Api.Simple<InvoiceItem.Category> {

    /**
     *
     * @param updatedSince
     *            if set, only return categories updated since the given timestamp
     * @return a list of all InvoiceItemCategories in the account, sorted by
     *         creation date, newest first.
     */
    List<InvoiceItem.Category> list(@Nullable Instant updatedSince);

    /**
     * Return a list of invoiceItemCategories, sorted by creation date, newest
     * first. Use the filter object to filter the list. Page and perPage allow
     * controlling how many results to return.
     *
     * @param updatedSince
     *            if set, only return categories updated since the given timestamp
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of InvoiceItemCategory
     */
    Pagination<InvoiceItem.Category> list(@Nullable Instant updatedSince, int page, int perPage);

    /**
     * Return an existing InvoiceItemCategory.
     *
     * @param invoiceItemCategoryReference
     *            a reference to an existing InvoiceItemCategory
     * @return the full InvoiceItemCategory object
     */
    @Override
    InvoiceItem.Category get(Reference<InvoiceItem.Category> invoiceItemCategoryReference);

    /**
     * Create a new InvoiceItemCategory
     *
     * @param creationInfo
     *            creation information
     * @return the created InvoiceItemCategory
     */
    @Override
    InvoiceItem.Category create(InvoiceItem.Category creationInfo);

    /**
     * Updates the specific invoiceItemCategory by setting the values of the
     * parameters passed. Any parameters not provided will be left unchanged
     *
     * @param invoiceItemCategoryReference
     *            An existing InvoiceItemCategory to be updated
     * @param toChange
     *            the changes to be performed
     * @return the updated InvoiceItemCategory
     */
    InvoiceItem.Category update(Reference<InvoiceItem.Category> invoiceItemCategoryReference,
            InvoiceItem.Category toChange);

    /**
     * Delete an existing InvoiceItemCategory.
     *
     * @param invoiceItemCategoryReference
     *            a reference to the InvoiceItemCategory to be deleted
     */
    @Override
    void delete(Reference<InvoiceItem.Category> invoiceItemCategoryReference);
}
