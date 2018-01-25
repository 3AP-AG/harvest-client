package ch.aaap.harvestclient.util;

import ch.aaap.harvestclient.core.Harvest;

public class TestSetupUtil {

    public static Harvest getAdminClient() {

        String token = "1476610.pt._0MoGfrcciMq-kyEUHZjxk7yPYCqGY-Y3VNLgzv7nZ2V7PjE6lIpgPWIkWbRwP76gmKybiPy4w1K0IkcHniFgw";
        String accountID = "872477";

        return new Harvest(token, accountID);
    }

    public static Harvest getUserClient() {

        String token = "1478287. pt.dbjpoCh2g77EVCzIPgJylXKTD5LoEZj7mds47BdxYweJRG1fnmQBKqMvqlV0OxKYubpY6VcBn9iGaF3EerVkpA";
        String accountID = "872477";

        return new Harvest(token, accountID);

    }

    public static Harvest getOauthClient() {

        String token = "1476610.at.CW9AKHNG7JJZu0IIE7-h43ZQ0hhYiA8U1ExIPBm-pYbAisgUMy4bQ_JqSokghjml1R4t7kqdncGT_xXhA87CeA";

        return new Harvest(token, "");

    }
}
