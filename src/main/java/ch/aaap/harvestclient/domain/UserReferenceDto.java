package ch.aaap.harvestclient.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.domain.reference.UserReference;

/**
 * Used to deserialize user references in other APIs, e.g. from the TimeEntry
 * list
 */
public class UserReferenceDto implements UserReference {

    private static final Logger log = LoggerFactory.getLogger(UserReferenceDto.class);

    private Long id;

    private String name;

    public static UserReferenceDto of(long id) {
        return new UserReferenceDto(id);
    }

    public UserReferenceDto(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserReferenceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
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

}
