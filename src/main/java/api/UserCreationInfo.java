package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserCreationInfo {

    private static final Logger log = LoggerFactory.getLogger(UserCreationInfo.class);

    private final Map<String, Object> options;

    public UserCreationInfo(Map<String, Object> options) {
        this.options = options;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public static class Builder {
        private Map<String, Object> options = new HashMap<>();

        public Builder firstName(String firstName) {
            options.put("first_name", firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            options.put("last_name", lastName);
            return this;
        }

        public Builder email(String email) {
            options.put("email", email);
            return this;
        }

        public UserCreationInfo build() {
            // check mandatory parameters
            return new UserCreationInfo(options);
        }
    }
}
