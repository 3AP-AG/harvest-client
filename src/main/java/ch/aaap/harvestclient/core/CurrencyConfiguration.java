package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.exception.HarvestRuntimeException;

public class CurrencyConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CurrencyConfiguration.class);

    /**
     * Map currency code to currency name, e.g CHF -> Swiss Franc
     */
    private final Map<String, String> currencies;

    public CurrencyConfiguration(InputStream in) {
        if (in == null) {
            throw new HarvestRuntimeException("Inputstream for currency config is null");
        }
        try {
            currencies = parse(in);
            log.debug("Loaded {} currencies", currencies.size());
        } catch (IOException e) {
            throw new HarvestRuntimeException(e);
        }
    }

    private Map<String, String> parse(InputStream in) throws IOException {

        Map<String, String> parsed = new HashMap<>();

        try (InputStreamReader reader = new InputStreamReader(in)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] split = line.split("\\t");
                String name = split[0];
                String code = split[1];

                log.trace("Found {} with code {}", name, code);

                parsed.put(code, name);
            }
        }
        return parsed;
    }

    /**
     * @param code
     *            a three letter currency code (e.g. CHF)
     * @return true if this currency code will be recognized by Harvest
     */
    public boolean isValidCode(String code) {
        return currencies.containsKey(code);
    }

    /**
     *
     * @param code
     *            a three letter currency code
     * @return the full name of the currency, or Optional.empty() if the code is not
     *         valid
     * @see #isValidCode(String)
     */
    public Optional<String> getCurrencyName(String code) {
        return Optional.ofNullable(currencies.get(code));
    }

}
