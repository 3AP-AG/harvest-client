package ch.aaap.harvestclient.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface EstimateLineCategory extends BaseObject<EstimateLineCategory> {

    String getName();

}
