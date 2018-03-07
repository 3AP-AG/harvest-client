package ch.aaap.harvestclient.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.ImmutableUserReferenceDto;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public abstract class Role implements BaseObject<Role> {

    public abstract String getName();

    /**
     * The IDs of the users assigned to this role.
     */
    public abstract List<Long> getUserIds();

    public List<Reference<User>> getUserReferences() {
        return getUserIds().stream()
                .map(id -> ImmutableUserReferenceDto.builder()
                        .id(id)
                        .name("")
                        .build())
                .collect(Collectors.toList());
    }

}
