package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.InvoiceFilter;
import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoiceItem;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.InvoiceImportInfo;
import ch.aaap.harvestclient.domain.param.InvoiceItemUpdateInfo;
import ch.aaap.harvestclient.domain.param.InvoiceUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * @see <a href=
 *      "https://help.getharvest.com/api-v2/invoices-api/invoices/invoices/">
 *      Invoices API on Harvest</a>
 */
@Api.Permission(Api.Role.PROJECT_MANAGER)
public interface InvoicesApi extends Api.Simple<Invoice> {

    /**
     * Return a list of invoices, sorted by issue date, newest first. Use the filter
     * object to filter the list.
     *
     * @param filter
     *            filtering options
     * @return a (filtered) list of Invoices
     */
    List<Invoice> list(InvoiceFilter filter);

    /**
     * Return a list of invoices, sorted by issue date, newest first. Use the filter
     * object to filter the list. Page and perPage allow controlling how many
     * results to return.
     * 
     * @param filter
     *            filtering options
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a (filtered) list of Invoices
     */
    Pagination<Invoice> list(InvoiceFilter filter, int page, int perPage);

    /**
     * @param invoiceReference
     *            a reference to an existing Invoice
     * @return Return a full Invoice object
     */
    @Override
    Invoice get(Reference<Invoice> invoiceReference);

    /**
     * Create a new Invoice according to given creation information.
     *
     * @param invoiceCreationInfo
     *            the creation options
     * @return the newly created Invoice
     */
    @Override
    Invoice create(Invoice invoiceCreationInfo);

    /**
     * Create a new Invoice based on time tracked and expense.
     *
     * @param invoiceCreationInfo
     *            the creation options
     * @return the newly created Invoice
     */
    Invoice createFrom(InvoiceImportInfo invoiceCreationInfo);

    /**
     * Create a new invoice line item and add it to the given invoice
     * 
     * @param invoiceReference
     *            the invoice to add the item to
     * @param creationInfo
     *            the line item to be created
     * @return the updated Invoice
     */
    Invoice addLineItem(Reference<Invoice> invoiceReference, InvoiceItem creationInfo);

    /**
     * Update an existing line item
     *
     * @param invoiceReference
     *            the invoice that contains the item
     * @param updateInfo
     *            the line item to be updated
     * @param invoiceItemReference
     *            the line item to be deleted
     * @return the updated Invoice
     */
    Invoice updateLineItem(Reference<Invoice> invoiceReference, Reference<InvoiceItem> invoiceItemReference,
            InvoiceItemUpdateInfo updateInfo);

    /**
     * Delete an existing line item
     *
     * @param invoiceReference
     *            the invoice that contains the item
     * @param invoiceItemReference
     *            the line item to be deleted
     * @return the updated Invoice
     */
    Invoice deleteLineItem(Reference<Invoice> invoiceReference, Reference<InvoiceItem> invoiceItemReference);

    /**
     * Create new invoice line items and add it to the given invoice
     *
     * @param invoiceReference
     *            the invoice to add the item to
     * @param creationInfoList
     *            a list of items to be created
     * @return the updated Invoice
     */
    Invoice addLineItems(Reference<Invoice> invoiceReference, List<InvoiceItem> creationInfoList);

    /**
     * Update an existing line item
     *
     * @param invoiceReference
     *            the invoice that contains the item
     * @param invoiceItemReferenceList
     *            a list of the item references to be updated, in the same order as
     *            updateInfoList
     * @param updateInfoList
     *            a list of the line items to be updated
     * @return the updated Invoice
     */
    Invoice updateLineItems(Reference<Invoice> invoiceReference,
            List<? extends Reference<InvoiceItem>> invoiceItemReferenceList,
            List<InvoiceItemUpdateInfo> updateInfoList);

    /**
     * Delete an existing line item
     *
     * @param invoiceReference
     *            the invoice that contains the item
     * @param invoiceItemReferenceList
     *            a list of the line items to be deleted
     * @return the updated Invoice
     */
    Invoice deleteLineItems(Reference<Invoice> invoiceReference,
            List<? extends Reference<InvoiceItem>> invoiceItemReferenceList);

    /**
     * Updates an existing Invoice with the properties set in InvoiceUpdateInfo. Be
     * careful: setting a list of InvoiceItem will add them to the existing ones,
     * not replace them. See {@link #deleteLineItems(Reference, List)} to delete
     * existing items
     *
     * @param invoiceReference
     *            the existing invoice to be updated
     * @param toChange
     *            the properties to be updated
     * @return the updated Invoice
     */
    Invoice update(Reference<Invoice> invoiceReference, InvoiceUpdateInfo toChange);

    /**
     * Delete an existing Invoice.
     *
     * @param invoiceReference
     *            a reference to an existing Invoice to be deleted
     */
    @Override
    void delete(Reference<Invoice> invoiceReference);
}
