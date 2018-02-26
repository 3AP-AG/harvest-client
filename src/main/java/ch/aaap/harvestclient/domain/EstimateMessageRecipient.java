package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface EstimateMessageRecipient {
    @Nullable
    @Value.Parameter(order = 1)
    String getName();

    @Value.Parameter(order = 2)
    String getEmail();
}
