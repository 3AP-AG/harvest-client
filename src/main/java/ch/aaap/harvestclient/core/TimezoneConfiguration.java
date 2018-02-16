package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZoneId;
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

    private Map<String, ZoneId> harvestToZoneId = new HashMap<>();
    private Map<ZoneId, String> zoneIdToHarvest = new HashMap<>();

    public TimezoneConfiguration(InputStream in) {
        if (in == null) {
            throw new HarvestRuntimeException("Inputstream for timezone config is null");
        }
        try {
            parseTimezones(in);
            log.debug("Loaded {} timezones", harvestToZoneId.size());
        } catch (IOException e) {
            throw new HarvestRuntimeException(e);
        }
    }

    private void parseTimezones(InputStream in) throws IOException {

        try (InputStreamReader reader = new InputStreamReader(in)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] split = line.split("\\t");
                String harvestName = split[0];
                String zoneIdName = split[1];

                log.trace("Parsed {} and {}", harvestName, zoneIdName);

                ZoneId zone = ZoneId.of(zoneIdName);
                harvestToZoneId.put(harvestName, zone);
                zoneIdToHarvest.put(zone, harvestName);
            }
        }
    }

    /**
     * @param timezone
     *            the name of a timezone
     * @return true if the timezone will be recognized by Harvest
     */
    public boolean isValidName(String timezone) {
        return harvestToZoneId.containsKey(timezone);
    }

    /**
     * @param timezone
     *            the name of a timezone as recognized by Harvest
     * @return a Zoneoffset if the timezone is valid, Optional.empty() otherwise
     */
    public Optional<ZoneId> getZoneId(String timezone) {
        return Optional.ofNullable(harvestToZoneId.get(timezone));
    }

    /**
     *
     * Note: there is no one-to-one correspondence between Harvest timezones and
     * ZoneIds: this function returns the last one seen in timezones.txt
     * 
     * @param zoneId
     *            the ZoneId
     * @return the name of an Harvest timezone that maps back to this zoneId
     */
    public Optional<String> getHarvestName(ZoneId zoneId) {
        return Optional.ofNullable(zoneIdToHarvest.get(zoneId));
    }
}
