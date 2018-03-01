package ch.aaap.harvestclient.core.gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    private static final String DTO_SUFFIX = "ReferenceDto";

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        Class<? super T> rawType = type.getRawType();
        log.trace("Called create with {}", type);

        if (rawType != Reference.class) {
            return null;
        }

        Class<?> elementClass = parseReferenceType(type);
        String simpleName = elementClass.getSimpleName();
        String expectedClassName = simpleName + DTO_SUFFIX;

        try {

            Class<?> dtoClass = Class.forName(BaseReferenceDto.class.getPackage().getName() + "." + expectedClassName);
            log.trace("Got Dto class {}", dtoClass);

            TypeAdapter<?> delegate = gson.getAdapter(dtoClass);
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
            throw new HarvestRuntimeException("Could not find ReferenceDto. Reference<" + simpleName
                    + "> needs a class named " + expectedClassName + " to existing in the same package as ReferenceDto",
                    e);
        }
    }

    /**
     * Given a typeToken for {@code Reference<T>}, return T
     * 
     * @param typeToken
     *            TypeToken representing Reference<T>
     * @param <T>
     *            the element class
     * @return a Class<T> object
     */
    private <T> Class<?> parseReferenceType(TypeToken<T> typeToken) {

        // Reference is parametrized
        Type type = typeToken.getType();
        Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
        return TypeToken.get(elementType).getRawType();
    }

}
