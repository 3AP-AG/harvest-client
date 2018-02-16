package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface EstimateLineItem {
    @Nullable
    Long getId();

    /**
     * @return the estimate Item category
     */
    @Nullable
    String getKind();

    @Nullable
    String getDescription();

    @Nullable
    Long getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();

}
