package ch.aaap.harvestclient.core.gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.BaseReferenceDto;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;

/**
 * A {@code Reference<T>} field can be either a full object (like a Client) or a
 * ReferenceDto (e.g. ClientReferenceDto). When serializing, we only need an id
 * -> {@code client_id:123213}, while we deserialize expecting a ReferenceDto
 */
public class ReferenceDtoAdapter implements TypeAdapterFactory {

    private static final Logger log = LoggerFactory.getLogger(ReferenceDtoAdapter.class);

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        Class<? super T> rawType = type.getRawType();
        log.trace("Called create with {}", type);

        if (rawType != Reference.class) {
            return null;
        }

        try {
            Class<?> elementClass = parseReferenceType(type);

            // TODO document convention
            Class<?> dtoClass = Class.forName(
                    BaseReferenceDto.class.getPackage().getName() + "." + elementClass.getSimpleName()
                            + "ReferenceDto");
            log.trace("Got Dto class {}", dtoClass);

            TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(dtoClass));
            log.trace("Returning type adapter for {}", type);

            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }
                    Reference reference = (Reference) value;
                    out.value(reference.getId());
                }

                @Override
                @SuppressWarnings("unchecked")
                public T read(JsonReader in) throws IOException {
                    return (T) delegate.read(in);
                }
            };
        } catch (ClassNotFoundException e) {
            throw new HarvestRuntimeException("Could not find ReferenceDto", e);
        }
    }

    /**
     * Given a type for {@code Reference<T>}, return T
     * 
     * @param type
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    private <T> Class<?> parseReferenceType(TypeToken<T> type) throws ClassNotFoundException {
        // TODO ugly hack
        Pattern pattern = Pattern.compile("([^<>]+)<([^<>]+)>");
        log.debug("Matching on {}", type.toString());
        Matcher matcher = pattern.matcher(type.toString());
        if (!matcher.matches()) {
            throw new HarvestRuntimeException("Something wrong here");
        }
        String elementTypeName = matcher.group(2);
        return Class.forName(elementTypeName);
    }

}
