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
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TimeEntry;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;

public class TestSetupUtil {

    private static final Logger log = LoggerFactory.getLogger(TestSetupUtil.class);
    private static final Random random = new Random();
    private static TimeEntry fixEntry;

    public static Harvest getAdminAccess() {

        return new Harvest(ConfigFactory.load("admin"));
    }

    public static UserCreationInfo getRandomUserCreationInfo() {
        int i = random.nextInt(1000_000);
        return new UserCreationInfo("first" + i, "last" + i, "test" + i + "@example.com");
    }

    public static Client getExistingClient() {

        Client client = new Client();
        // TODO create this before the whole test suite
        client.setId(6422922L);

        return client;
    }

    public static Project getExistingProject() {
        Reference<Project> projectReference = new ProjectReferenceDto(16227254);
        return getAdminAccess().projects().get(projectReference);
    }

    public static Task getExistingTask() {
        // TODO implement TasksApi
        return null;
    }

    public static TimeEntry getFixedTimeEntry() {

        if (fixEntry == null) {
            // TODO create fix entries

            log.debug("Creating fixed Entry");

            TimeEntry entry = new TimeEntry();
            entry.setId(738720479L);
            fixEntry = getAdminAccess().timesheets().get(entry);
        }

        return fixEntry;
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
}
