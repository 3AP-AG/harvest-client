package ch.aaap.harvestclient.domain.pagination;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface PaginationLinks {

    @Nullable
    String getFirst();

    @Nullable
    String getNext();

    @Nullable
    String getPrevious();

    @Nullable
    String getLast();

}
