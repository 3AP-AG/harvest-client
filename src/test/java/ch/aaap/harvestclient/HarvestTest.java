package ch.aaap.harvestclient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * To be applied on all Test classes, so that we can print out the testMethod
 * names to stdout
 */
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(Junit5Extension.class)
public @interface HarvestTest {
}