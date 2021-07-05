package ch.aaap.harvestclient.domain.reference.dto;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ProjectReferenceDto extends BaseReferenceDto, Reference<Project> {

    @Nullable
    String getCode();

}
