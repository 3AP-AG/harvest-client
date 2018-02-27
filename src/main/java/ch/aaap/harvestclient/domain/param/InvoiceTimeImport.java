package ch.aaap.harvestclient.domain.param;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoiceTimeImport {

    enum SummaryType {
        @SerializedName("project")
        PROJECT,

        @SerializedName("task")
        TASK,

        @SerializedName("people")
        PEOPLE,

        @SerializedName("detailed")
        DETAILED
    }

    SummaryType getSummaryType();

    @SerializedName("from")
    @Nullable
    LocalDate getFromDate();

    @Nullable
    LocalDate getTo();
}
