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
