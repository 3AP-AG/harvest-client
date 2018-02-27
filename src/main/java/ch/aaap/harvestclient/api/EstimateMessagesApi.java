package ch.aaap.harvestclient.api;

import java.time.Instant;
import java.util.List;

import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.EstimateMessage;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface EstimateMessagesApi extends Api.GetNested<Estimate, EstimateMessage> {

    /**
     *
     * @param updatedSince
     *            return only EstimateMessages that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param estimateReference
     *            the estimate containing the messages
     * @return a list of all EstimateMessages in the estimate, sorted by creation
     *         date, newest first.
     */
    List<EstimateMessage> list(Reference<Estimate> estimateReference, Instant updatedSince);

    /**
     *
     * @param updatedSince
     *            return only EstimateMessages that have been updated at least 1
     *            second after updatedSince. Set to null to disable filtering.
     * @param estimateReference
     *            the estimate containing the messages
     * @param page
     *            the page number
     * @param perPage
     *            how many results to return for one page. Max 100
     * @return a list of all EstimateMessages in the estimate, sorted by creation
     *         date, newest first.
     */
    Pagination<EstimateMessage> list(Reference<Estimate> estimateReference, Instant updatedSince, int page,
            int perPage);

    /**
     * Return an existing EstimateMessage.
     *
     * @param estimateReference
     *            the estimate containing the message
     * @param estimateMessageReference
     *            a reference to an existing EstimateMessage
     * @return the full EstimateMessage object
     */
    @Override
    EstimateMessage get(Reference<Estimate> estimateReference, Reference<EstimateMessage> estimateMessageReference);

    /**
     * Create a new EstimateMessage
     * 
     * @param estimateReference
     *            the estimate that will contain the message
     * @param creationInfo
     *            creation information
     * @return the created EstimateMessage
     */
    EstimateMessage create(Reference<Estimate> estimateReference, EstimateMessage creationInfo);

    /**
     * Change the state of an estimate to the given eventType. Not all transitions
     * are allowed: see the web interface for more information
     * 
     * @param estimateReference
     *            the estimate against which to record the event
     * @param eventType
     *            the event you want to record for this estimate
     */
    EstimateMessage markAs(Reference<Estimate> estimateReference, EstimateMessage.EventType eventType);

    /**
     * Delete an existing EstimateMessage. Deleting an Accept Message does not
     * change the status of the Estimate.
     *
     * @param estimateReference
     *            the estimate that contains the message
     * @param estimateMessageReference
     *            An existing EstimateMessage to be deleted
     */
    void delete(Reference<Estimate> estimateReference, Reference<EstimateMessage> estimateMessageReference);

}
