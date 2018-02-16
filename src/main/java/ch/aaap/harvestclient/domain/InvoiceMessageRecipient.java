package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface InvoiceMessageRecipient {
    @Nullable
    String getName();

    String getEmail();
}
