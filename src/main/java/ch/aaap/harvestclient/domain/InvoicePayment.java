package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface InvoicePayment extends BaseObject<InvoicePayment> {

    String getAmount();

    @Nullable
    Instant getPaidAt();

    @Nullable
    LocalDate getPaidDate();

    @Nullable
    String getRecordedBy();

    @Nullable
    String getRecordedByEmail();

    @Nullable
    String getNotes();

    @Nullable
    String getTransactionId();

    @Nullable
    PaymentGateway getPaymentGateway();

}
