package ch.aaap.harvestclient.domain.param;

import java.util.List;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Harvest API for EstimateItem is irregular, we need another container object
 * (we cannot reuse Estimate)
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface LineItemContainer {

    List<Object> getLineItems();

}
