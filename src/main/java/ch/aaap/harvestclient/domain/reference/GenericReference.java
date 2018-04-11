package ch.aaap.harvestclient.domain.reference;

/**
 * Usually this is only needed in glue code, you can use T or a T Dto instead
 * 
 * @param <T>
 *            the object type referred to
 */
public class GenericReference<T> implements Reference<T> {

    private final long id;

    private GenericReference(long id) {
        this.id = id;
    }

    public static <T> GenericReference<T> of(long id) {
        return new GenericReference<>(id);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GenericReference{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GenericReference<?> that = (GenericReference<?>) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
