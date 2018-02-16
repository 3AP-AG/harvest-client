package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

@Value.Immutable
public interface ClientContactUpdateInfo {

    @Nullable
    ClientReferenceDto getClientReferenceDto();

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
