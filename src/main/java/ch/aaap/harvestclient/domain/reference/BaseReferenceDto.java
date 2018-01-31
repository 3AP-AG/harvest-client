package ch.aaap.harvestclient.domain.reference;

import java.util.Objects;

/**
 * Used to deserialize objects references in other APIs, e.g. from the TimeEntry
 * list
 */
public class BaseReferenceDto {

    Long id;
    String name;

    public BaseReferenceDto() {

    }

    public BaseReferenceDto(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BaseReferenceDto that = (BaseReferenceDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
