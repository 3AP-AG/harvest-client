package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.Retainer;
import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Hold all properties common to various InvoiceInfo objects
 */
public interface InvoiceCommonInfo {
    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    @Nullable
    Retainer getRetainer();

    @Nullable
    Estimate getEstimate();

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

    @Nullable
    LocalDate getDueDate();
}
