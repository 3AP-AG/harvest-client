package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.ImmutableUser;
import ch.aaap.harvestclient.domain.User;

public class TestSetupUtil {

    private static final Logger log = LoggerFactory.getLogger(TestSetupUtil.class);
    private static final Random random = new Random();

    private static class LazyHolder {
        static final Harvest adminAccess = new Harvest(ConfigFactory.load("admin1"));
        static final Harvest anotherAdminAccess = new Harvest(ConfigFactory.load("admin2"));

    }

    public static Harvest getAdminAccess() {
        return LazyHolder.adminAccess;
    }

    public static Harvest getAnotherAdminAccess() {
        return LazyHolder.anotherAdminAccess;
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
}
