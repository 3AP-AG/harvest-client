package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.domain.reference.Reference;

public class UserReferenceDto extends BaseReferenceDto implements Reference<User> {

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
