package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoiceItemUpdateInfo {

    @Nullable
    Long getId();

    @Nullable
    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProject();

    @Nullable
    String getKind(); // Invoice Item Category

    @Nullable
    String getDescription();

    @Nullable
    Double getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();

}
