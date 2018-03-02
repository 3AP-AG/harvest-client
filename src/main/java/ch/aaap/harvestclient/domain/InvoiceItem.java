package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Enclosing
public interface InvoiceItem extends Reference<InvoiceItem> {

    /**
     * The category that is stored in getKind() as a String
     */
    @Gson.TypeAdapters(fieldNamingStrategy = true)
    @Value.Immutable
    interface Category extends BaseObject<Category> {

        // Immutables bug: cannot use @Value.Parameter here, so the javadoc below is not
        // applicable. See https://github.com/immutables/immutables/issues/754
        /*
         * We only provide means to set Name since the booleans cannot be set at all.
         * Only the Default Item already created by Harvest have them set to true
         */
        String getName();

        @Nullable
        Boolean getUseAsService();

        @Nullable
        Boolean getUseAsExpense();
    }

    @Override
    @Nullable
    Long getId();

    @Nullable
    @SerializedName(value = "project_id", alternate = "project")
    Reference<Project> getProject();

    String getKind(); // Invoice Item Category

    /**
     * max length = 65,535
     */
    @Nullable
    String getDescription();

    @Nullable
    Long getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();

}
