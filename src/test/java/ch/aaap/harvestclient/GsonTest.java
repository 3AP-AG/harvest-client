package ch.aaap.harvestclient;

import java.lang.reflect.Type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ch.aaap.harvestclient.core.gson.GsonConfiguration;
import ch.aaap.harvestclient.domain.pagination.PaginatedList;

/**
 * Here we can test deserialization for various Java objects
 */
@HarvestTest
class GsonTest {

    private static final Logger log = LoggerFactory.getLogger(GsonTest.class);

    @Test
    void testPaginationDeserialization() {

        Gson gson = GsonConfiguration.getConfiguration();

        String message = "{\"users\":[{\"id\":1971929,\"first_name\":\"George\",\"last_name\":\"Frank\",\"email\":\"george@example.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":false,\"is_project_manager\":false,\"can_see_rates\":false,\"can_create_projects\":false,\"can_create_invoices\":false,\"is_active\":true,\"created_at\":\"2018-01-29T15:15:49Z\",\"updated_at\":\"2018-01-29T15:15:49Z\",\"default_hourly_rate\":null,\"cost_rate\":null,\"roles\":[],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/abraj_albait_towers.png?1517238949\"},{\"id\":1971921,\"first_name\":\"First\",\"last_name\":\"Last\",\"email\":\"test300872@example.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":false,\"is_project_manager\":false,\"can_see_rates\":false,\"can_create_projects\":false,\"can_create_invoices\":false,\"is_active\":true,\"created_at\":\"2018-01-29T15:14:19Z\",\"updated_at\":\"2018-01-29T15:14:19Z\",\"default_hourly_rate\":null,\"cost_rate\":null,\"roles\":[],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/cornell_clock_tower.png?1517238859\"},{\"id\":1971915,\"first_name\":\"First\",\"last_name\":\"Last\",\"email\":\"test300871@example.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":false,\"is_project_manager\":false,\"can_see_rates\":false,\"can_create_projects\":false,\"can_create_invoices\":false,\"is_active\":true,\"created_at\":\"2018-01-29T15:13:16Z\",\"updated_at\":\"2018-01-29T15:20:50Z\",\"default_hourly_rate\":null,\"cost_rate\":null,\"roles\":[],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/big_ben.png?1517238796\"},{\"id\":1967878,\"first_name\":\"FixFirst\",\"last_name\":\"FixLast\",\"email\":\"fix.user@example.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":false,\"is_project_manager\":false,\"can_see_rates\":false,\"can_create_projects\":false,\"can_create_invoices\":false,\"is_active\":true,\"created_at\":\"2018-01-25T10:30:36Z\",\"updated_at\":\"2018-01-31T11:38:01Z\",\"default_hourly_rate\":null,\"cost_rate\":null,\"roles\":[],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/allen_bradley_clock_tower.png?1516876236\"},{\"id\":1965434,\"first_name\":\"First\",\"last_name\":\"Last\",\"email\":\"marco.nembrini.co+testUser@gmail.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":false,\"is_project_manager\":false,\"can_see_rates\":false,\"can_create_projects\":false,\"can_create_invoices\":false,\"is_active\":true,\"created_at\":\"2018-01-23T10:44:07Z\",\"updated_at\":\"2018-01-23T10:44:56Z\",\"default_hourly_rate\":100.0,\"cost_rate\":120.0,\"roles\":[\"Designer\"],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/big_ben.png?1516704247\"},{\"id\":1963640,\"first_name\":\"Marco\",\"last_name\":\"Nembrini\",\"email\":\"marco.nembrini.co@gmail.com\",\"telephone\":\"\",\"timezone\":\"Berlin\",\"weekly_capacity\":151200,\"has_access_to_all_future_projects\":false,\"is_contractor\":false,\"is_admin\":true,\"is_project_manager\":false,\"can_see_rates\":true,\"can_create_projects\":true,\"can_create_invoices\":true,\"is_active\":true,\"created_at\":\"2018-01-22T08:45:27Z\",\"updated_at\":\"2018-01-22T08:45:27Z\",\"default_hourly_rate\":null,\"cost_rate\":null,\"roles\":[],\"avatar_url\":\"https://cache.harvestapp.com/assets/profile_images/big_ben.png?1516610727\"}],\"per_page\":100,\"total_pages\":1,\"total_entries\":6,\"next_page\":null,\"previous_page\":null,\"page\":1,\"links\":{\"first\":\"https://api.harvestapp.com/v2/users?page=1&per_page=100\",\"next\":null,\"previous\":null,\"last\":\"https://api.harvestapp.com/v2/users?page=1&per_page=100\"}}";

        Type listType = new TypeToken<PaginatedList>() {
        }.getType();
        PaginatedList list = gson.fromJson(message, listType);

        log.debug("list is {} ", list);

        Assertions.assertEquals("https://api.harvestapp.com/v2/users?page=1&per_page=100",
                list.getLinks().getFirst());

        Assertions.assertEquals("George", list.getUsers().get(0).getFirstName());

    }
}
