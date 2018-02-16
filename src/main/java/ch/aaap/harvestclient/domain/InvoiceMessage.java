package ch.aaap.harvestclient.domain;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface InvoiceMessage extends BaseObject<InvoiceMessage> {
    @Nullable
    String getSentBy();

    @Nullable
    String getSentByEmail();

    @Nullable
    String getSentFrom();

    @Nullable
    String getSentFromEmail();

    List<InvoiceMessageRecipient> getInvoiceMessageRecipients();

    @Nullable
    String getSubject();

    @Nullable
    String getBody();

    @Nullable
    Boolean getIncludeLinkToClientInvoice();

    @Nullable
    Boolean getAttachPdf();

    @Nullable
    Boolean getSendMeACopy();

    @Nullable
    Boolean getThankYou();

    @Nullable
    Boolean getEventType();

    @Nullable
    Boolean getReminder();

    @Nullable
    LocalDate getSendReminderOn();

}
