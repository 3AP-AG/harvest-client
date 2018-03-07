package ch.aaap.harvestclient.domain.param;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Represents a creation or update request for a Role
 */
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface RoleInfo {

    @Value.Parameter
    String getName();

    @Nullable
    List<Long> getUserIds();
}
