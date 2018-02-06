package util;

import java.util.Random;

import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.param.UserCreationInfo;

public class TestSetupUtil {

    private static final Random random = new Random();

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

}
