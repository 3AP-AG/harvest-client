package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoiceImportInfo extends InvoiceCommonInfo {

    @Override
    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    @Nullable
    InvoiceItemImport getLineItemsImport();

}
