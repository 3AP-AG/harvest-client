package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.reference.UserReference;

public class UserReferenceDto extends BaseReferenceDto implements UserReference {

    public UserReferenceDto() {
    }

    public UserReferenceDto(long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "UserReferenceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
