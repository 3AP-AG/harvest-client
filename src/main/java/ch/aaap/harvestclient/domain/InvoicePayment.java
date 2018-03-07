package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface InvoicePayment extends BaseObject<InvoicePayment> {

    Double getAmount();

    @Nullable
    Instant getPaidAt();

    @Nullable
    LocalDate getPaidDate();

    @Nullable
    String getRecordedBy();

    @Nullable
    String getRecordedByEmail();

    /**
     * max length = 16,777,215
     * 
     * @return the current value
     */
    @Nullable
    String getNotes();

    @Nullable
    String getTransactionId();

    @Nullable
    PaymentGateway getPaymentGateway();

}
