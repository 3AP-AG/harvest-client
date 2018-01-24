package domain.param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    private static final Logger log = LoggerFactory.getLogger(UserInfo.class);

    private final Map<String, Object> options;

    protected UserInfo(Map<String, Object> options) {
        this.options = options;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public static class Builder {
        protected Map<String, Object> options = new HashMap<>();


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

        public UserInfo build() {
            return new UserInfo(options);
        }
    }

}

