package core;

import com.google.gson.Gson;
import core.gson.GsonConfiguration;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.TimeEntryService;

import java.io.IOException;

public class Harvest {

    private static final String BASE_URL = "https://api.harvestapp.com/v2/";

    private TimeEntryService timeEntryService;


    public Harvest() {

        HttpLoggingInterceptor debugInterceptor = new HttpLoggingInterceptor();
        debugInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

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

        timeEntryService = retrofit.create(TimeEntryService.class);

    }


    public TimeEntryService getTimeEntryService() {
        return timeEntryService;
    }
}
