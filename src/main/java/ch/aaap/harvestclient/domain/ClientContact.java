package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Value.Immutable
public interface ClientContact extends BaseObject<ClientContact> {

    @SerializedName(value = "client_id", alternate = "client")
    Reference<Client> getClient();

    @Nullable
    String getTitle();

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
