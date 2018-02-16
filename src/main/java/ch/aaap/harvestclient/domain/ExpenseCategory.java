package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ExpenseCategory extends BaseObject<ExpenseCategory> {

    String getName();

    @Nullable
    String getUnitName();

    @Nullable
    Double getUnitPrice();

    @Value.Default
    default Boolean getActive() {
        return true;
    }

}
