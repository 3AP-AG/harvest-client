package ch.aaap.harvestclient.domain.param;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ClientUpdateInfo {

    @Nullable
    Long getId();

    @Nullable
    String getName();

    @SerializedName("is_active")
    @Nullable
    Boolean getActive();

    @Nullable
    String getAddress();

    @Nullable
    String getCurrency();

}
