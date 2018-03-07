package ch.aaap.harvestclient.api;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Generic interface for the different Harvest API.
 */
public interface Api {

    /**
     * Types of permissions needed to use a certain api
     */
    enum Role {
        /**
         * Normal User
         */
        NONE,

        /**
         * Project Manager for the specific project
         */
        PROJECT_MANAGER,

        /**
         * Harvest admin
         */
        ADMIN
    }

    /**
     * Describes the kind of role the user of the api needs.
     */
    @interface Permission {
        Role value();

        /**
         * If true, a user can only read/write information about himself: needs admin
         * permissions to change objects for other users
         * 
         * @return the current value
         */
        boolean onlySelf() default false;
    }

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
