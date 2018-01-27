package ch.aaap.harvestclient.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.UsersApi;
import ch.aaap.harvestclient.core.gson.GsonConfiguration;
import ch.aaap.harvestclient.impl.TimesheetsApiImpl;
import ch.aaap.harvestclient.impl.UsersApiImpl;
import ch.aaap.harvestclient.service.TimeEntryService;
import ch.aaap.harvestclient.service.UserService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    
    private TimesheetsApi timesheetsApi;

    private UsersApi usersApi;


    public Harvest(Config config) {

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

        timesheetsApi = new TimesheetsApiImpl(timeEntryService);
        usersApi = new UsersApiImpl(userService);

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
