package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;

public class UserCreationInfo extends User {

    public UserCreationInfo(String firstName, String lastName, String email) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    @Override
    public void setId(Long id) {
        throw new HarvestRuntimeException("Cannot set id for new User, will be assigned by Harvest");
    }

    @Override
    public String toString() {
        return super.toStringFull();
    }
}
