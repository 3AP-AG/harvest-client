package ch.aaap.harvestclient.domain.param;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.InvoiceItem;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface InvoiceUpdateInfo extends InvoiceCommonInfo {

    @SerializedName("line_items")
    @Nullable
    List<InvoiceItem> getInvoiceItems();
}
