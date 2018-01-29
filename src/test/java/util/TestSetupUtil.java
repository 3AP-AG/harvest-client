package util;

import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.core.Harvest;

public class TestSetupUtil {

    public static Harvest getAdminAccess() {

        return new Harvest(ConfigFactory.load("admin"));
    }

}
