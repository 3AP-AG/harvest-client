package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Enclosing
public interface EstimateItem extends Reference<EstimateItem> {

    /**
     * The category that is stored in getKind() as a String
     */
    @Gson.TypeAdapters(fieldNamingStrategy = true)
    @Value.Immutable
    interface Category extends BaseObject<Category> {

        String getName();

    }

    @Override
    @Nullable
    Long getId();

    /**
     * @return the estimate Item category name
     */
    String getKind();

    /**
     * max length = 65,535
     * 
     * @return the current value
     */
    @Nullable
    String getDescription();

    @Nullable
    Long getQuantity();

    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();

}
