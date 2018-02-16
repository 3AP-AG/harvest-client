package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface EstimateMessageRecipient {
    @Nullable
    String getName();

    String getEmail();
}
