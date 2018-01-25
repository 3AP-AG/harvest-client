package core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import api.TimesheetsApi;
import api.UsersApi;
import core.gson.GsonConfiguration;
import impl.TimesheetsApiImpl;
import impl.UsersApiImpl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.TimeEntryService;
import service.UserService;

public class Harvest {

    private static final Logger log = LoggerFactory.getLogger(Harvest.class);

    private static final String BASE_URL = "https://api.harvestapp.com/v2/";

    private TimesheetsApi timesheetsApi;

    private UsersApi usersApi;

    /**
     * The authentication bearer token used for every request. Can either be a
     * personal token or a OAuth2 token, see
     * https://help.getharvest.com/api-v2/authentication-api/authentication/authentication
     */
    private String authToken;

    /** Account id. Only needed for personal tokens */
    private String accountId;

    private String userAgent = "harvest-client (https://github.com/3AP-AG/harvest-client)";

    public Harvest(String authToken, String accountId) {

        this.authToken = authToken;
        this.accountId = accountId;

        Interceptor debugInterceptor = initHttpLogging();
        Interceptor authenticationInterceptor = initAuthentication();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                // debug interceptor goes last
                .addInterceptor(debugInterceptor)
                .build();

        Gson gson = GsonConfiguration.getConfiguration();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeEntryService timeEntryService = retrofit.create(TimeEntryService.class);
        UserService userService = retrofit.create(UserService.class);

        timesheetsApi = new TimesheetsApiImpl(timeEntryService);
        usersApi = new UsersApiImpl(userService);

        log.debug("Harvest client initialized");
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
        Logger httpLogger = LoggerFactory.getLogger("okhttp");
        HttpLoggingInterceptor debugInterceptor = new HttpLoggingInterceptor(httpLogger::trace);
        debugInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return debugInterceptor;
    }

    public TimesheetsApi timesheets() {
        return timesheetsApi;
    }

    public UsersApi users() {
        return usersApi;
    }
}
