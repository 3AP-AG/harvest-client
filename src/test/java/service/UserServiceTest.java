package service;

import core.Harvest;
import domain.User;
import domain.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static UserService service;
    private static Harvest harvest;


    @BeforeAll
    public static void setup() {
        harvest = new Harvest();
        service = harvest.getUserService();

    }

    @Test
    void listAll() throws IOException {

        Call<Users> call = service.listAll();
        Response<Users> entries = call.execute();
        assertTrue(entries.body().getUsers().size() == 1);
        for (User user : entries.body().getUsers()) {
            System.out.println(user);
        }
    }
}