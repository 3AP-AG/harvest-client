package ch.aaap.harvestclient.core;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CurrencyConfigurationTest {

    @Test
    void parseDefault() {

        InputStream in = CurrencyConfiguration.class.getResourceAsStream("/currencies.txt");

        CurrencyConfiguration configuration = new CurrencyConfiguration(in);

        assertThat(configuration.isValidCode("CHF")).isTrue();

        assertThat(configuration.getCurrencyName("CHF").get()).isEqualTo("Swiss Franc");

    }
}