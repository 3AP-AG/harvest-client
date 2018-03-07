package ch.aaap.harvestclient.domain;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.google.gson.annotations.SerializedName;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
@Value.Style.Depluralize
public interface EstimateMessage extends BaseObject<EstimateMessage> {
    enum EventType {

        @SerializedName("send")
        SEND,

        @SerializedName("accept")
        ACCEPT,

        @SerializedName("decline")
        DECLINE,

        @SerializedName("re-open")
        RE_OPEN,

        @SerializedName("view")
        VIEW,

        @SerializedName("invoice")
        INVOICE
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
     * 
     * @return the current value
     */
    @Nullable
    String getBody();

    @Nullable
    Boolean getSendMeACopy();

    @Nullable
    EventType getEventType();

}
