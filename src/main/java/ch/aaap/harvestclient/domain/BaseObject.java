package ch.aaap.harvestclient.domain;

import java.time.Instant;

import javax.annotation.Nullable;

import ch.aaap.harvestclient.domain.reference.Reference;

public interface BaseObject<T> extends Reference<T> {

    @Override
    @Nullable
    Long getId();

    @Nullable
    Instant getCreatedAt();

    @Nullable
    Instant getUpdatedAt();

}
