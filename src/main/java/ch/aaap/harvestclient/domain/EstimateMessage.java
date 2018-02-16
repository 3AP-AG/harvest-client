package ch.aaap.harvestclient.domain;

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface EstimateMessage extends BaseObject<EstimateMessage> {

    @Nullable
    String getSentBy();

    @Nullable
    String getSentByEmail();

    @Nullable
    String getSentFrom();

    @Nullable
    String getSentFromEmail();

    List<EstimateMessageRecipient> getInvoiceMessageRecipients();

    @Nullable
    String getSubject();

    @Nullable
    String getBody();

    @Nullable
    Boolean getSendMeACopy();

    @Nullable
    Boolean getEventType();

}
