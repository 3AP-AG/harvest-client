package ch.aaap.harvestclient.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Harvest API does not expose more information about retainers
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface Retainer extends Reference<Retainer> {

}
