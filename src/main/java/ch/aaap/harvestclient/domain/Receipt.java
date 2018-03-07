package ch.aaap.harvestclient.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

/**
 * Only documented in comment
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Receipt {

    String getUrl();

    @SerializedName("file_name")
    String getFilename();
}
