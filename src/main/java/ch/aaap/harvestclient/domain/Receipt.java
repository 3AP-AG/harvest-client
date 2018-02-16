package ch.aaap.harvestclient.domain;

import org.immutables.value.Value;

/**
 * Only documented in comment
 */
@Value.Immutable
public interface Receipt {

    String getUrl();

    String getFilename();
}
