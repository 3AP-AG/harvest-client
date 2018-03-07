package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface Estimate extends BaseObject<Estimate> {

    /**
     * Not documented online: got this from Harvest:
     * <p>
     * In the mean time you should be able to use the estimate's sent_at,
     * accepted_at, and declined_at fields to determine the estimate's state.
     * <ul>
     * <li>If all three are empty, the estimate is a draft</li>
     * <li>If only sent_at is present, the estimate is open (or sent)</li>
     * <li>If accepted_at is present, the estimate is accepted</li>
     * <li>If declined_at is present, the estimate is declined (accepted_at and
     * declined_at are mutually exclusive)</li>
     * </ul>
     *
     */
    enum State {

        DRAFT,

        OPEN,

        ACCEPTED,

        DECLINED
    }

    default State getState() {

        if (getAcceptedAt() != null) {
            return State.ACCEPTED;
        }
        if (getDeclinedAt() != null) {
            return State.DECLINED;
        }
        if (getSentAt() != null) {
            return State.OPEN;
        }
        return State.DRAFT;
    }

    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    @SerializedName("line_items")
    @Nullable
    List<EstimateItem> getEstimateItems();

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
     * 
     * @return the current value
     */
    @Nullable
    String getNotes();

    @Nullable
    String getCurrency();

    @Nullable
    LocalDate getIssueDate();

    @Nullable
    Instant getSentAt();

    @Nullable
    Instant getAcceptedAt();

    @Nullable
    Instant getDeclinedAt();

}
