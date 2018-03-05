package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface PaymentGateway extends Reference<PaymentGateway> {

    @Override
    @Nullable
    Long getId();

    @Nullable
    String getName();

}
