package ch.aaap.harvestclient.domain;

import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Value.Immutable
public interface Company {

    String getBaseUri();

    String getFullDomain();

    String getName();

    @SerializedName("is_active")
    Boolean getActive();

    String getWeekStartDay();

    Boolean getWantsTimestampTimers();

    String getTimeFormat();

    String getPlanType();

    String getClock();

    String getDecimalSymbol();

    String getThousandsSeparator();

    String getColorScheme();

    Boolean getExpenseFeature();

    Boolean getInvoiceFeature();

    Boolean getEstimateFeature();

    Boolean getApprovalFeature();

}
