package ch.aaap.harvestclient.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.api.*;
import ch.aaap.harvestclient.core.gson.GsonConfiguration;
import ch.aaap.harvestclient.impl.*;
import ch.aaap.harvestclient.service.*;
import ch.aaap.harvestclient.vendor.okhttp.HttpLoggingInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Note: Harvest datatype mappings: boolean -> Boolean string -> String integer
 * -> Long decimal -> Double date -> LocalDate datetime -> Instant time ->
 * LocalTime, but depends on harvest config for parsing </>array -> List<T>
 * object -> Object
 *
 */
public class Harvest {

    private static final Logger log = LoggerFactory.getLogger(Harvest.class);

    private final String baseUrl;

    /**
     * The authentication bearer token used for every request. Can either be a
     * personal token or a OAuth2 token, see
     * https://help.getharvest.com/api-v2/authentication-api/authentication/authentication
     */
    private final String authToken;

    /** Account id. Only needed for personal tokens */
    private final String accountId;

    private final String userAgent;
    private final RolesApi rolesApi;
    private final ProjectAssignmentsApiImpl projectAssignmentsApi;

    private TimesheetsApi timesheetsApi;

    private UsersApi usersApi;

    private CompanyApi companyApi;

    public Harvest(Config config) {

        // TODO we might need to allow missing account id for oauth2
        config.checkValid(ConfigFactory.defaultReference(), "harvest");

        this.baseUrl = config.getString("harvest.baseUrl");
        this.userAgent = config.getString("harvest.userAgent");
        this.authToken = config.getString("harvest.auth.token");
        this.accountId = config.getString("harvest.auth.accountId");

        Interceptor debugInterceptor = initHttpLogging();
        Interceptor authenticationInterceptor = initAuthentication();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                // debug interceptor goes last
                .addInterceptor(debugInterceptor)
                .build();

        Gson gson = GsonConfiguration.getConfiguration();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeEntryService timeEntryService = retrofit.create(TimeEntryService.class);
        UserService userService = retrofit.create(UserService.class);
        CompanyService companyService = retrofit.create(CompanyService.class);
        RoleService roleService = retrofit.create(RoleService.class);
        ProjectAssignmentService projectAssignmentService = retrofit.create(ProjectAssignmentService.class);

        timesheetsApi = new TimesheetsApiImpl(timeEntryService);
        usersApi = new UsersApiImpl(userService);
        companyApi = new CompanyApiImpl(companyService);
        rolesApi = new RolesApiImpl(roleService);

        projectAssignmentsApi = new ProjectAssignmentsApiImpl(projectAssignmentService);

        log.debug("Harvest client initialized");

    }

    public Harvest() {
        this(ConfigFactory.load());
    }

    private Interceptor initAuthentication() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader("Authorization", "Bearer " + authToken);
                ongoing.addHeader("Harvest-Account-id", accountId);
                ongoing.addHeader("User-Agent", userAgent);
                return chain.proceed(ongoing.build());
            }
        };
    }

    private Interceptor initHttpLogging() {
        // fictional http package
        Logger httpLogger = LoggerFactory.getLogger(Harvest.class.getName() + ".http");

        HttpLoggingInterceptor debugInterceptor = new HttpLoggingInterceptor(httpLogger::trace);
        // if someone is setting this to TRACE, they probably want all the information
        debugInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return debugInterceptor;
    }

    public TimesheetsApi timesheets() {
        return timesheetsApi;
    }

    public UsersApi users() {
        return usersApi;
    }

    public CompanyApi company() {
        return companyApi;
    }

    public RolesApi roles() {
        return rolesApi;
    }

    public ProjectAssignmentsAPI projectAssignments() {
        return projectAssignmentsApi;
    }
}
