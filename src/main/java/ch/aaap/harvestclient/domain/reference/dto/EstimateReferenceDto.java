package ch.aaap.harvestclient.domain.reference.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface EstimateReferenceDto extends Reference<Estimate> {

}
