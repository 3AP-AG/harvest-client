package examples;

import core.Harvest;
import domain.User;
import org.junit.jupiter.api.Test;
import util.TestSetupUtil;

import java.util.List;

public class UsersApiExample {


    @Test
    public void listUsers() {
        Harvest harvest = TestSetupUtil.getClient();

        List<User> users = harvest.users().list();

        for (User user : users) {
            System.out.println(user);
        }
    }
}
