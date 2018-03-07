package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ExpenseCategoryUpdateInfo {

    @Nullable
    String getName();

    @Nullable
    String getUnitName();

    @Nullable
    Double getUnitPrice();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

}
