package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Enclosing
public interface EstimateItem {

    /**
     * The category that is stored in getKind() as a String
     */
    @Gson.TypeAdapters(fieldNamingStrategy = true)
    @Value.Immutable
    interface Category extends BaseObject<Category> {

        String getName();

    }

    @Nullable
    Long getId();

    /**
     * @return the estimate Item category name
     */
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
