package domain.param;

import java.util.Map;

public class UserCreationInfo extends UserInfo {

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
