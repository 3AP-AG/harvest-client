package core.gson;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConfiguration {

    /**
     * Configure the GSON JSON parser for the specific formats used by Harvest
     */
    public static Gson getConfiguration() {

        // convert java.time dates using ISO 8601
        GsonBuilder gsonBuilder = Converters.registerAll(new GsonBuilder());
        // override parsing for LocalTime (it looks like '4:04pm')
        // TODO this depends on the company account settings (12 vs 24h format)
        // gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeConverter());

        // a field 'externalService' is serialized to 'external_service'

        return gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    }
}
