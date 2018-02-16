package ch.aaap.harvestclient.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Only documented in comment
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Receipt {

    String getUrl();

    String getFilename();
}
