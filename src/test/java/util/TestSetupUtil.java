package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableUser;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;

public class TestSetupUtil {

    private static final Logger log = LoggerFactory.getLogger(TestSetupUtil.class);
    private static final Random random = new Random();

    private static final int testRunId = random.nextInt(1_000_000_000);

    private static class LazyHolder {
        static final Harvest adminAccess = new Harvest(loadConfiguration("admin1"));
    }

    private static class LazyHolder2 {
        static final Harvest anotherAdminAccess = new Harvest(loadConfiguration("admin2"));
    }

    public static Harvest getAdminAccess() {
        return LazyHolder.adminAccess;
    }

    public static Harvest getAnotherAdminAccess() {
        return LazyHolder2.anotherAdminAccess;
    }

    /**
     * Try to load a local conf file first, and override values with environment
     * variables if they are set.
     * 
     * @param name
     *            the name of the configuration to load. Allowed characters are the
     *            same as for environment variables (A-Z, a-z, 0-9, _)
     * @return the configuration with env overrides applied
     */
    private static Config loadConfiguration(String name) {
        // check for allowed characters
        if (name.matches("^[A-Za-z0-9_]]")) {
            throw new HarvestRuntimeException("Configuration name " + name + " contains invalid characters");
        }

        Config config = ConfigFactory.load(name);

        String harvestAccountId = System.getenv("HARVEST_ACCOUNT_ID_" + name.toUpperCase());
        String harvestAuthToken = System.getenv("HARVEST_AUTH_TOKEN_" + name.toUpperCase());
        if (harvestAccountId != null) {
            config = config.withValue("harvest.auth.accountId", ConfigValueFactory.fromAnyRef(harvestAccountId));
        }
        if (harvestAuthToken != null) {
            config = config.withValue("harvest.auth.token", ConfigValueFactory.fromAnyRef(harvestAuthToken));
        }

        return config;

    }

    public static User getRandomUserCreationInfo() {
        int i = random.nextInt(1000_000);
        return ImmutableUser.builder()
                .firstName("first" + i)
                .lastName("last" + i)
                .email("test" + i + "@example.com")
                .build();
    }

    public static void prepareForHarvestBugReport() {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);

        try {
            URI harvestLogConfig = TestSetupUtil.class.getClassLoader()
                    .getResource("log4j2-harvest-bug-report.xml")
                    .toURI();

            // this will force a reconfiguration
            context.setConfigLocation(harvestLogConfig);
        } catch (URISyntaxException e) {
            log.error("", e);
        }

    }

    /**
     * Return an email address where received emails are immediately deleted -> can
     * be used as email for harvest notifications generated from tests
     */
    public static String getDevNullEmail() {
        return "marco.nembrini.co+devnull@gmail.com";
    }

    public static int getTestRunId() {
        return testRunId;
    }
}
