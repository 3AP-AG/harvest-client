package util;

import ch.aaap.harvestclient.domain.reference.Reference;

/**
 * Not to be used outside of testing data! Gives up on type safety for
 * references
 * 
 * @param <T>
 *            the object type referred to
 */
class GenericReference<T> implements Reference<T> {

    private Long id;

    GenericReference(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
