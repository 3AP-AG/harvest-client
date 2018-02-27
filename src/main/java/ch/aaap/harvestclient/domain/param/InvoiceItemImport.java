package ch.aaap.harvestclient.domain.param;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoiceItemImport {

    @SerializedName("project_ids")
    List<Reference<Project>> getProjects();

    @Nullable
    @SerializedName("time")
    InvoiceTimeImport getInvoiceTimeImport();

    @Nullable
    @SerializedName("expenses")
    InvoiceExpenseImport getExpenseImport();

}
