package ch.aaap.harvestclient.domain;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Nullable;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface TeamReport extends BaseObject<TeamReport> {

    /**
     * The ID of the user associated with the reported hours.
     */
    @Nullable
    Integer getUserId();

    /**
     * The name of the user associated with the reported hours.
     */
    @Nullable
    String getUserName();

    /**
     * The contractor status of the user associated with the reported hours.
     */
    @SerializedName("is_contractor")
    @Nullable
    Boolean contractor();

    /**
     * The totaled hours for the given timeframe, subject (client, project, task, or user), and currency.
     * If Time Rounding is turned on, the hours will be rounded according to your settings.
     */
    @Nullable
    Double getTotalHours();

    /**
     * The totaled billable hours for the given timeframe, subject (client, project, task, or user), and currency. If Time Rounding is turned on, the hours will be rounded according to your settings.
     */
    Double getBillableHours();

    /**
     * The currency code associated with the tracked hours for this result.
     */
    String getCurrency();

    /**
     * The totaled billable amount for the billable hours above.
     */
    Double getBillableAmount();
}
