package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;

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
 * Main client class. All API access starts from this.
 * <p>
 *
 *
 *
 * Note for developers: Harvest datatype mappings: {@literal
 * boolean -> Boolean
 * string -> String
 * integer-> Long
 * decimal -> Double
 * date -> LocalDate
 * datetime -> Instant
 * time -> LocalTime, but depends on harvest config for parsing
 * array -> List<T>
 * object -> Object
 * }
 *
 */
public class Harvest {

    private static final Logger log = LoggerFactory.getLogger(Harvest.class);

    /**
     * URL to the Harvest v2 instance (e.g. "https://api.harvestapp.com/v2/" )
     */
    private final String baseUrl;

    /**
     * The authentication bearer token used for every request. Can either be a
     * personal token or a OAuth2 token, see
     * https://help.getharvest.com/api-v2/authentication-api/authentication/authentication
     */
    private final String authToken;

    /** Account id. Only needed for personal tokens */
    private final String accountId;

    private final Config config;

    /**
     * What User-Agent to send. Harvest policy is to include either an email or the
     * URL for the client code.
     */
    private final String userAgent;

    private final TimezoneConfiguration timezoneConfiguration;
    private final CurrencyConfiguration currencyConfiguration;

    private final RolesApi rolesApi;
    private final ProjectAssignmentsApi projectAssignmentsApi;
    private final ProjectsApi projectsApi;
    private final TaskAssignmentsApi taskAssignmentsApi;
    private final TasksApi tasksApi;
    private final TimesheetsApi timesheetsApi;
    private final UsersApi usersApi;
    private final CompanyApi companyApi;
    private final ClientsApi clientsApi;
    private final ClientContactsApi clientContactsApi;
    private final EstimatesApi estimatesApi;
    private final EstimateItemCategoriesApi estimateItemCategoriesApi;
    private final EstimateMessagesApi estimateMessagesApi;
    private final InvoiceItemCategoriesApi invoiceItemCategoriesApi;

    public Harvest(Config config) {

        this.config = config;

        // TODO we might need to allow missing account id for oauth2
        config.checkValid(ConfigFactory.defaultReference(), "harvest");

        this.baseUrl = config.getString("harvest.baseUrl");
        this.userAgent = config.getString("harvest.userAgent");
        this.authToken = config.getString("harvest.auth.token");
        this.accountId = config.getString("harvest.auth.accountId");

        Interceptor debugInterceptor = initHttpLogging();
        Interceptor authenticationInterceptor = initAuthentication();

        Retrofit retrofit = initRetrofit(debugInterceptor, authenticationInterceptor);

        timezoneConfiguration = new TimezoneConfiguration(openConfiguredStream("harvest.timezones_path"));
        currencyConfiguration = new CurrencyConfiguration(openConfiguredStream("harvest.currencies_path"));

        TimeEntryService timeEntryService = retrofit.create(TimeEntryService.class);
        UserService userService = retrofit.create(UserService.class);
        CompanyService companyService = retrofit.create(CompanyService.class);
        RoleService roleService = retrofit.create(RoleService.class);
        ProjectAssignmentService projectAssignmentService = retrofit.create(ProjectAssignmentService.class);
        ProjectService projectService = retrofit.create(ProjectService.class);
        TaskAssignmentService taskAssignmentService = retrofit.create(TaskAssignmentService.class);
        TaskService taskService = retrofit.create(TaskService.class);
        ClientService clientService = retrofit.create(ClientService.class);
        ClientContactService clientContactService = retrofit.create(ClientContactService.class);
        EstimateService estimateService = retrofit.create(EstimateService.class);
        EstimateItemCategoryService estimateItemCategoryService = retrofit.create(EstimateItemCategoryService.class);
        EstimateMessagesService estimateMessagesService = retrofit.create(EstimateMessagesService.class);
        InvoiceItemCategoryService invoiceItemCategoryService = retrofit.create(InvoiceItemCategoryService.class);

        timesheetsApi = new TimesheetsApiImpl(timeEntryService);
        usersApi = new UsersApiImpl(userService);
        companyApi = new CompanyApiImpl(companyService);
        rolesApi = new RolesApiImpl(roleService);
        projectAssignmentsApi = new ProjectAssignmentsApiImpl(projectAssignmentService);
        projectsApi = new ProjectsApiImpl(projectService);
        taskAssignmentsApi = new TaskAssignmentsApiImpl(taskAssignmentService);
        tasksApi = new TasksApiImpl(taskService);
        clientsApi = new ClientsApiImpl(clientService);
        clientContactsApi = new ClientContactsApiImpl(clientContactService);
        estimatesApi = new EstimatesApiImpl(estimateService);
        estimateItemCategoriesApi = new EstimateItemCategoriesApiImpl(estimateItemCategoryService);
        estimateMessagesApi = new EstimateMessagesApiImpl(estimateMessagesService);
        invoiceItemCategoriesApi = new InvoiceItemCategoriesApiImpl(invoiceItemCategoryService);

        log.debug("Harvest client initialized");

    }

    private InputStream openConfiguredStream(String configName) {
        String resourcePath = config.getString(configName);
        return Harvest.class.getResourceAsStream(resourcePath);
    }

    private Retrofit initRetrofit(Interceptor debugInterceptor, Interceptor authenticationInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                // debug interceptor goes last
                .addInterceptor(debugInterceptor)
                .build();

        Gson gson = GsonConfiguration.getConfiguration();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
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

    public ProjectAssignmentsApi projectAssignments() {
        return projectAssignmentsApi;
    }

    public ProjectsApi projects() {
        return projectsApi;
    }

    public TaskAssignmentsApi taskAssignments() {
        return taskAssignmentsApi;
    }

    public TasksApi tasks() {
        return tasksApi;
    }

    public ClientsApi clients() {
        return clientsApi;
    }

    public ClientContactsApi clientContacts() {
        return clientContactsApi;
    }

    public EstimatesApi estimates() {
        return estimatesApi;
    }

    public EstimateItemCategoriesApi estimateItemCategories() {
        return estimateItemCategoriesApi;
    }

    public EstimateMessagesApi estimateMessages() {
        return estimateMessagesApi;
    }

    public InvoiceItemCategoriesApi invoiceItemCategories() {
        return invoiceItemCategoriesApi;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public TimezoneConfiguration getTimezoneConfiguration() {
        return timezoneConfiguration;
    }

    public CurrencyConfiguration getCurrencyConfiguration() {
        return currencyConfiguration;
    }

}
