package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TimezoneConfigurationTest {

    @Test
    void parseDefault() throws IOException {

        InputStream inputStream = TimezoneConfiguration.class.getResourceAsStream("/timezones.txt");

        TimezoneConfiguration configuration = new TimezoneConfiguration(inputStream);

        assertThat(configuration.isValidName("Mumbai")).isTrue();
        assertThat(configuration.getOffset("Mumbai").get()).isEqualTo(ZoneOffset.of("+05:00"));

    }

}