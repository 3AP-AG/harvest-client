package ch.aaap.harvestclient.domain;

import com.google.gson.annotations.SerializedName;

public class Company {

    private String baseUri;

    private String fullDomain;

    private String name;

    @SerializedName("is_active")
    private Boolean active;

    private String weekStartDay;

    private Boolean wantsTimestampTimers;

    private String timeFormat;

    private String planType;

    private String clock;

    private String decimalSymbol;

    private String thousandsSeparator;

    private String colorScheme;

    private Boolean expenseFeature;

    private Boolean invoiceFeature;

    private Boolean estimateFeature;

    private Boolean approvalFeature;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getFullDomain() {
        return fullDomain;
    }

    public void setFullDomain(String fullDomain) {
        this.fullDomain = fullDomain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getWeekStartDay() {
        return weekStartDay;
    }

    public void setWeekStartDay(String weekStartDay) {
        this.weekStartDay = weekStartDay;
    }

    public Boolean getWantsTimestampTimers() {
        return wantsTimestampTimers;
    }

    public void setWantsTimestampTimers(Boolean wantsTimestampTimers) {
        this.wantsTimestampTimers = wantsTimestampTimers;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getDecimalSymbol() {
        return decimalSymbol;
    }

    public void setDecimalSymbol(String decimalSymbol) {
        this.decimalSymbol = decimalSymbol;
    }

    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public Boolean getExpenseFeature() {
        return expenseFeature;
    }

    public void setExpenseFeature(Boolean expenseFeature) {
        this.expenseFeature = expenseFeature;
    }

    public Boolean getInvoiceFeature() {
        return invoiceFeature;
    }

    public void setInvoiceFeature(Boolean invoiceFeature) {
        this.invoiceFeature = invoiceFeature;
    }

    public Boolean getEstimateFeature() {
        return estimateFeature;
    }

    public void setEstimateFeature(Boolean estimateFeature) {
        this.estimateFeature = estimateFeature;
    }

    public Boolean getApprovalFeature() {
        return approvalFeature;
    }

    public void setApprovalFeature(Boolean approvalFeature) {
        this.approvalFeature = approvalFeature;
    }

    @Override
    public String toString() {
        return "Company{" +
                "baseUri='" + baseUri + '\'' +
                ", fullDomain='" + fullDomain + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", weekStartDay='" + weekStartDay + '\'' +
                ", wantsTimestampTimers=" + wantsTimestampTimers +
                ", timeFormat='" + timeFormat + '\'' +
                ", planType='" + planType + '\'' +
                ", clock='" + clock + '\'' +
                ", decimalSymbol='" + decimalSymbol + '\'' +
                ", thousandsSeparator='" + thousandsSeparator + '\'' +
                ", colorScheme='" + colorScheme + '\'' +
                ", expenseFeature=" + expenseFeature +
                ", invoiceFeature=" + invoiceFeature +
                ", estimateFeature=" + estimateFeature +
                ", approvalFeature=" + approvalFeature +
                '}';
    }
}
