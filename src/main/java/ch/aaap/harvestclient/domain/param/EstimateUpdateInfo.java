package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.InvoiceLineItem;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface EstimateUpdateInfo {

    @Nullable
    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    @Nullable
    List<InvoiceLineItem> getInvoiceLineItemList();

    @Nullable
    String getNumber();

    @Nullable
    String getPurchaseOrder();

    @Nullable
    Double getTax();

    @Nullable
    Double getTax2();

    @Nullable
    Double getDiscount();

    @Nullable
    String getSubject();

    @Nullable
    String getNotes();

    @Nullable
    String getCurrency();

    @Nullable
    LocalDate getIssueDate();

}
