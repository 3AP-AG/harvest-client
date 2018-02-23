package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Generic interface for the different Harvest API.
 */
public interface Api {

    /**
     * Common interface for simple rest objects
     * 
     * @param <T>
     *            the type for this api
     */
    interface Simple<T> extends Get<T>, Create<T>, Delete<T> {
    }

    interface Get<T> {
        T get(Reference<T> reference);
    }

    interface GetNested<C, T> {
        T get(Reference<C> contextReference, Reference<T> reference);
    }

    interface Create<T> {
        T create(T creationInfo);

    }

    interface Delete<T> {
        void delete(Reference<T> reference);
    }

}
