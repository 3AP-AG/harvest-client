package ch.aaap.harvestclient.core;

import java.io.InputStream;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TimezoneConfigurationTest {

    @Test
    void parseDefault() {

        InputStream inputStream = TimezoneConfiguration.class.getResourceAsStream("/timezones.txt");

        TimezoneConfiguration configuration = new TimezoneConfiguration(inputStream);

        assertThat(configuration.isValidName("Mumbai")).isTrue();

        assertThat(configuration.getZoneId("Mumbai").get()).isEqualTo(ZoneId.of("Asia/Kolkata"));
        // not one-to-one
        assertThat(configuration.getHarvestName(ZoneId.of("Asia/Kolkata")).get()).isEqualTo("New Delhi");
    }
}