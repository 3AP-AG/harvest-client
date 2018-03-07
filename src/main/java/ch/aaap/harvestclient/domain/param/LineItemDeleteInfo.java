package ch.aaap.harvestclient.domain.param;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface LineItemDeleteInfo {

    @Value.Parameter
    Long getId();

    @SerializedName("_destroy")
    @Value.Default
    default Boolean getDestroy() {
        return true;
    }
}
