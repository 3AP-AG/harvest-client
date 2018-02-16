package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoiceLineItem {
    @Nullable
    Long getId();

    @Nullable
    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProject();

    String getKind(); // Invoice Item Category

    @Nullable
    String getDescription();

    @Nullable
    Long getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();
}
