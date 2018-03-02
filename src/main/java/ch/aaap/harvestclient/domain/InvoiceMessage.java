package ch.aaap.harvestclient.domain;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface InvoiceMessage extends BaseObject<InvoiceMessage> {

    enum EventType {

        @SerializedName("send")
        SEND,

        /**
         * Write off the invoice
         */
        @SerializedName("close")
        CLOSE,

        @SerializedName("draft")
        DRAFT,

        @SerializedName("re-open")
        RE_OPEN,

        @SerializedName("view")
        VIEW

    }

    @Nullable
    String getSentBy();

    @Nullable
    String getSentByEmail();

    @Nullable
    String getSentFrom();

    @Nullable
    String getSentFromEmail();

    @SerializedName("recipients")
    List<MessageRecipient> getMessageRecipients();

    @Nullable
    String getSubject();

    /**
     * max length = 16,777,215
     */
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
    EventType getEventType();

    @Nullable
    Boolean getReminder();

    @Nullable
    LocalDate getSendReminderOn();

}
