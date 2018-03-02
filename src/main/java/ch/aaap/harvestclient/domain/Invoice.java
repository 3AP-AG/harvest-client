package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface Invoice extends BaseObject<Invoice> {

    /**
     * Not documented online: got this from Harvest:
     * <p>
     * For invoice's, you can use the sent_at, paid_at (or paid_date), closed_at,
     * and due_date fields to determine state:
     * <ul>
     * <li>If sent_at, paid_at, and closed_at are empty, the invoice is a draft</li>
     * <li>If only sent_at is present and the current date is < due_date, the
     * invoice is open</li>
     * <li>If only sent_at is present and the current date is >= due_date, the
     * invoice is late</li>
     * <li>If paid_at is present, the invoice is paid</li>
     * <li>If closed_at is present, the invoice has been closed (or
     * written-off)</li>
     * </ul>
     *
     */
    enum State {

        DRAFT,

        OPEN,

        LATE,

        PAID,

        CLOSED
    }

    /**
     * @param zoneId
     *            the timezone to use for deciding between LATE and OPEN
     * @return the invoice current State
     */
    default State getState(ZoneId zoneId) {

        if (getClosedAt() != null) {
            return State.CLOSED;
        }
        if (getPaidAt() != null || getPaidDate() != null) {
            return State.PAID;
        }
        if (getSentAt() != null) {
            if (getDueDate() == null) {
                throw new HarvestRuntimeException("Invalid object state, expected due_date not to be null for " + this);
            }
            if (getDueDate().isBefore(LocalDate.now(zoneId))) {
                return State.LATE;
            }
            return State.OPEN;
        }
        return State.DRAFT;
    }

    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    @SerializedName("line_items")
    @Nullable
    List<InvoiceItem> getInvoiceItems();

    @Nullable
    Reference<Estimate> getEstimate();

    @Nullable
    Retainer getRetainer();

    @Nullable
    Creator getCreator();

    @Nullable
    String getClientKey();

    @Nullable
    String getNumber();

    @Nullable
    String getPurchaseOrder();

    @Nullable
    Double getAmount();

    @Nullable
    Double getDueAmount();

    @Nullable
    Double getTax();

    @Nullable
    Double getTaxAmount();

    @Nullable
    Double getTax2();

    @Nullable
    Double getTaxAmount2();

    @Nullable
    Double getDiscount();

    @Nullable
    Double getDiscountAmount();

    @Nullable
    String getSubject();

    /**
     * max length = 65,535
     */
    @Nullable
    String getNotes();

    @Nullable
    String getCurrency();

    @Nullable
    LocalDate getPeriodStart();

    @Nullable
    LocalDate getPeriodEnd();

    @Nullable
    LocalDate getIssueDate();

    @Nullable
    LocalDate getDueDate();

    @Nullable
    Instant getSentAt();

    @Nullable
    Instant getPaidAt();

    @Nullable
    LocalDate getPaidDate();

    @Nullable
    Instant getClosedAt();

}
