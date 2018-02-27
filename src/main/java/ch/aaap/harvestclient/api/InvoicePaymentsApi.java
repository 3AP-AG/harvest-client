package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.InvoicePayment;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface InvoicePaymentsApi {
    /**
     *
     * @param updatedSince
     *            return only InvoicePayments that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param invoiceReference
     *            the invoice containing the payments
     * @return a list of all InvoicePayments in the invoice, sorted by creation
     *         date, newest first.
     */
    List<InvoicePayment> list(Reference<Invoice> invoiceReference, Instant updatedSince);

    /**
     *
     * @param updatedSince
     *            return only InvoicePayments that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param invoiceReference
     *            the invoice containing the payments
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all InvoicePayments in the invoice, sorted by creation
     *         date, newest first.
     */
    Pagination<InvoicePayment> list(Reference<Invoice> invoiceReference, Instant updatedSince, int page,
            int perPage);

    /**
     * Create a new InvoicePayment
     *
     * @param invoiceReference
     *            the invoice that will contain the payment
     * @param creationInfo
     *            creation information
     * @return the created InvoicePayment
     */
    InvoicePayment create(Reference<Invoice> invoiceReference, InvoicePayment creationInfo);

    /**
     * Delete an existing InvoicePayment.
     *
     * @param invoiceReference
     *            the invoice that contains the payment
     * @param invoicePaymentReference
     *            An existing InvoicePayment to be deleted
     */
    void delete(Reference<Invoice> invoiceReference, Reference<InvoicePayment> invoicePaymentReference);

}
