package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface EstimateItemUpdateInfo {

    @Nullable
    Long getId();

    /**
     * @return the estimate Item category name
     */
    @Nullable
    String getKind();

    @Nullable
    String getDescription();

    @Nullable
    Double getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();

}