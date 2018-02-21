package ch.aaap.harvestclient.domain.reference;

import java.util.Objects;

public interface Reference<T> {

    /**
     * Require that id is not null.
     * <p>
     *
     * Example: {@code list.forEach(Reference::requireId); }
     * 
     * @throws NullPointerException
     *             if id is null
     */
    static <T> void requireId(Reference<T> ref) {
        Objects.requireNonNull(ref.getId());
    }

    Long getId();
}
