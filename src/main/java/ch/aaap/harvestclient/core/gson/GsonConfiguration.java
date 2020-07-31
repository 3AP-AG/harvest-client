package ch.aaap.harvestclient.core.gson;

import ch.aaap.harvestclient.core.util.FormatUtil;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

/**
 * Harvest specific Gson configuration
 */
public class GsonConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GsonConfiguration.class);

    /**
     * Configure the GSON JSON parser for the specific formats used by Harvest
     * 
     * @param use12HoursFormat
     *            if true, convert LocalTime objects with '1:02pm' format
     * @return the global Gson configuration
     */
    public static Gson getConfiguration(boolean use12HoursFormat) {

        // convert java.time dates using ISO 8601
        GsonBuilder gsonBuilder = Converters.registerAll(new GsonBuilder());
        // override parsing for LocalTime (it looks like '4:04pm' or '16:04' depending
        // on Company settings
        DateTimeFormatter dateTimeFormatter = FormatUtil.getDateTimeFormatter(use12HoursFormat);

        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeConverter(dateTimeFormatter));

        gsonBuilder.registerTypeAdapterFactory(new ReferenceDtoAdapter());

        // register generated TypeAdapters
        int count = 0;
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
            count++;
        }
        log.debug("registered {} Generated Gson Adapters", count);

        return gsonBuilder
                // a field 'externalService' is serialized to 'external_service'
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

    }

}
