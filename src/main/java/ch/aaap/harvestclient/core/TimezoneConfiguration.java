package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.exception.HarvestRuntimeException;

/**
 * Keeps a list of supported harvest timezones and has methods to converted them
 * to ZoneOffsets.
 */
public class TimezoneConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TimezoneConfiguration.class);

    private final Map<String, ZoneOffset> timezones;

    public TimezoneConfiguration(InputStream in) {
        if (in == null) {
            throw new HarvestRuntimeException("Inputstream for timezone config is null");
        }
        try {
            timezones = parseTimezones(in);
            log.debug("Loaded {} timezones", timezones.size());
        } catch (IOException e) {
            throw new HarvestRuntimeException(e);
        }
    }

    private Map<String, ZoneOffset> parseTimezones(InputStream in) throws IOException {

        Map<String, ZoneOffset> parsed = new HashMap<>();

        try (InputStreamReader reader = new InputStreamReader(in)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] split = line.split("\\t");
                String name = split[0];
                String offset = split[1];

                log.trace("Found {} at offset {}", name, offset);

                parsed.put(name, parseOffset(offset));
            }
        }
        return parsed;
    }

    private ZoneOffset parseOffset(String offset) {
        if (!offset.startsWith("-")) {
            // add a plus
            offset = "+" + offset;
        }
        return ZoneOffset.of(offset);
    }

    /**
     * @param timezone
     *            the name of a timezone
     * @return true if the timezone will be recognized by Harvest
     */
    public boolean isValidName(String timezone) {
        return timezones.containsKey(timezone);
    }

    /**
     *
     * @param timezone
     *            the name of a timezone as recognized by Harvest
     * @return a Zoneoffset if the timezone is valid, Optional.empty() otherwise
     */
    public Optional<ZoneOffset> getOffset(String timezone) {
        return Optional.ofNullable(timezones.get(timezone));
    }

}
