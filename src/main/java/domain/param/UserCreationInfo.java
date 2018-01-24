package domain.param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserCreationInfo extends UserInfo {

    private static final Logger log = LoggerFactory.getLogger(UserCreationInfo.class);

    private UserCreationInfo(Map<String, Object> options) {
        super(options);
    }

    public static class Builder extends UserInfo.Builder {

        public Builder(String firstName, String lastName, String email) {
            // these parameters are mandatory
            firstName(firstName);
            lastName(lastName);
            email(email);
        }

        @Override
        public UserCreationInfo build() {
            return new UserCreationInfo(options);
        }
    }
}
