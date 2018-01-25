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


    public Harvest() {

        log.debug("Harvest client initialized");

        Logger httpLogger = LoggerFactory.getLogger("okhttp");
        HttpLoggingInterceptor debugInterceptor = new HttpLoggingInterceptor(httpLogger::trace);
        debugInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        // User token
        // 1478287. pt.dbjpoCh2g77EVCzIPgJylXKTD5LoEZj7mds47BdxYweJRG1fnmQBKqMvqlV0OxKYubpY6VcBn9iGaF3EerVkpA
        // 872477

        String token = "1476610.pt._0MoGfrcciMq-kyEUHZjxk7yPYCqGY-Y3VNLgzv7nZ2V7PjE6lIpgPWIkWbRwP76gmKybiPy4w1K0IkcHniFgw";
        String accountID = "872477";
        String userAgent = "harvest-client (https://github.com/3AP-AG/harvest-client)";
        Interceptor authenticationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader("Authorization", "Bearer " + token);
                ongoing.addHeader("Harvest-Account-id", accountID);
                ongoing.addHeader("User-Agent", userAgent);
                return chain.proceed(ongoing.build());
            }
        };


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

    }


    public TimesheetsApi timesheets() {
        return timesheetsApi;
    }

    public UsersApi users() {
        return usersApi;
    }
}
