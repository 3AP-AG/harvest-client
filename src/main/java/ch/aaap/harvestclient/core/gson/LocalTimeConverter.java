package ch.aaap.harvestclient.core.gson;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

/**
 * Non-standard LocalTime conversion
 */
public class LocalTimeConverter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    // private final DateTimeFormatter FORMATTER =
    // DateTimeFormatter.ofPattern("h:mma");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return FORMATTER.parse(json.getAsString().toUpperCase(), LocalTime::from);
    }
}
