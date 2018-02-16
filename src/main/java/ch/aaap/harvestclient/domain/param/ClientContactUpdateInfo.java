package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.reference.Reference;

@Value.Immutable
public interface ClientContactUpdateInfo {

    @SerializedName(value = "client_id", alternate = "client")
    @Nullable
    Reference<Client> getClient();

    @Nullable
    String getTitle();

    @Nullable
    String getFirstName();

    @Nullable
    String getLastName();

    @Nullable
    String getEmail();

    @Nullable
    String getPhoneOffice();

    @Nullable
    String getPhoneMobile();

    @Nullable
    String getFax();

}
