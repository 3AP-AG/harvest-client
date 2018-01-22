package core;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConfiguration {


    /**
     * Configure the GSON JSON parser for the spefic formats used by Harvest
     *
     * @return
     */
    public static Gson getConfiguration() {

        // convert java.time dates using ISO 8601
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder = Converters.registerAll(gsonBuilder);

        // a field 'externalService' is serialized to 'external_service'
        Gson gson = gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        return gson;

    }
}
