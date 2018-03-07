package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoiceMessage;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

@Api.Permission(Api.Role.ADMIN)
public interface InvoiceMessagesApi {

    /**
     *
     * @param updatedSince
     *            return only InvoiceMessages that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param invoiceReference
     *            the invoice containing the messages
     * @return a list of all InvoiceMessages in the invoice, sorted by creation
     *         date, newest first.
     */
    List<InvoiceMessage> list(Reference<Invoice> invoiceReference, Instant updatedSince);

    /**
     *
     * @param updatedSince
     *            return only InvoiceMessages that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param invoiceReference
     *            the invoice containing the messages
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all InvoiceMessages in the invoice, sorted by creation
     *         date, newest first.
     */
    Pagination<InvoiceMessage> list(Reference<Invoice> invoiceReference, Instant updatedSince, int page,
            int perPage);

    /**
     * Create a new InvoiceMessage
     * 
     * @param invoiceReference
     *            the invoice that will contain the message
     * @param creationInfo
     *            creation information
     * @return the created InvoiceMessage
     */
    InvoiceMessage create(Reference<Invoice> invoiceReference, InvoiceMessage creationInfo);

    /**
     * Change the state of an invoice to the given eventType. Not all transitions
     * are allowed: see the web interface for more information
     * 
     * @param invoiceReference
     *            the invoice against which to record the event
     * @param eventType
     *            the event you want to record for this invoice
     */
    InvoiceMessage markAs(Reference<Invoice> invoiceReference, InvoiceMessage.EventType eventType);

    /**
     * Delete an existing InvoiceMessage. Deleting an Accept Message does not change
     * the status of the Invoice.
     *
     * @param invoiceReference
     *            the invoice that contains the message
     * @param invoiceMessageReference
     *            An existing InvoiceMessage to be deleted
     */
    void delete(Reference<Invoice> invoiceReference, Reference<InvoiceMessage> invoiceMessageReference);

}
