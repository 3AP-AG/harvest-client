package ch.aaap.harvestclient.domain.reference.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface UserReferenceDto extends BaseReferenceDto, Reference<User> {

    @Override
    @Value.Parameter(order = 1)
    Long getId();

    @Override
    @Value.Parameter(order = 2)
    String getName();
}
