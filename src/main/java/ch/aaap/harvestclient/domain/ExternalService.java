package ch.aaap.harvestclient.domain;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Properties assumed from
 * https://help.getharvest.com/api-v2/timesheets-api/timesheets/time-entries/
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ExternalService extends Reference<ExternalService> {

    String getGroupId();

    String getPermalink();

    String getService();

    String getServiceIconUrl();

}
