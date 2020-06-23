package ch.aaap.harvestclient.core;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import ch.aaap.harvestclient.api.*;
import ch.aaap.harvestclient.core.gson.GsonConfiguration;
import ch.aaap.harvestclient.core.ratelimit.RateLimitInterceptor;
import ch.aaap.harvestclient.domain.User;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;
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
    private final UserAssignmentsApi userAssignmentsApi;
    private final CompanyApi companyApi;
    private final ClientsApi clientsApi;
    private final ClientContactsApi clientContactsApi;
    private final EstimatesApi estimatesApi;
    private final EstimateItemCategoriesApi estimateItemCategoriesApi;
    private final EstimateMessagesApi estimateMessagesApi;
    private final InvoiceItemCategoriesApi invoiceItemCategoriesApi;
    private final InvoicesApi invoicesApi;
    private final InvoicePaymentsApi invoicePaymentsApi;
    private final InvoiceMessagesApi invoiceMessagesApi;
    private final ExpenseCategoriesApi expenseCategoriesApi;
    private final ExpensesApi expensesApi;
    private final TeamReportsApi teamReportsApi;

    public Harvest() {
        this(ConfigFactory.load());
    }

    /**
     * Check reference.conf under src/main/resources for the default configuration.
     * You can either create a application.conf file that you put in your own
     * src/main/resources that will override the settings, or build a Config object
     * from scratch with {@link ConfigFactory}
     * <p>
     * Example:
     * 
     * <pre>
     * // use application.conf under src/main/resources
     * Harvest harvest = new Harvest();
     *
     * // start from defaults
     * Config config = ConfigFactory.defaultReference();
     * config = config.withValue("harvest.auth.token", ConfigValueFactory.fromAnyRef("YOUR_TOKEN"));
     * config = config.withValue("harvest.auth.accountId", ConfigValueFactory.fromAnyRef("YOUR_ACCOUNT_ID"));
     * harvest = new Harvest(config)
     * </pre>
     * 
     * @param config
     *            the configuration to be used for this client.
     *
     * @throws ch.aaap.harvestclient.exception.HarvestHttpException
     *             if we fail to get the Company object (to configure the clock
     *             format)
     *
     * @see <a href="https://github.com/lightbend/config">Config library on
     *      Github</a>
     *
     * @see <a href="https://id.getharvest.com/developers">Harvest auth token
     *      page</a>
     */
    public Harvest(Config config) {

        this.config = config;
        this.baseUrl = config.getString("harvest.baseUrl");
        this.userAgent = config.getString("harvest.userAgent");
        this.authToken = config.getString("harvest.auth.token");
        this.accountId = config.getString("harvest.auth.accountId");

        Interceptor debugInterceptor = initHttpLogging();
        Interceptor authenticationInterceptor = initAuthentication();

        int maxRequestWindow = config.getInt("harvest.max_request_per_window");
        int windowSize = config.getInt("harvest.window_size_seconds");
        Interceptor rateLimitInterceptor = new RateLimitInterceptor(maxRequestWindow, windowSize);

        Retrofit retrofit = initRetrofit(debugInterceptor, authenticationInterceptor, rateLimitInterceptor, false);
        boolean use12HoursFormat = getCompanyClockFormat(retrofit);
        // reinitialize with correct format if needed
        if (use12HoursFormat) {
            retrofit = initRetrofit(debugInterceptor, authenticationInterceptor, rateLimitInterceptor, true);
        }

        timezoneConfiguration = new TimezoneConfiguration(openConfiguredStream("harvest.timezones_path"));
        currencyConfiguration = new CurrencyConfiguration(openConfiguredStream("harvest.currencies_path"));

        TimeEntryService timeEntryService = retrofit.create(TimeEntryService.class);
        UserService userService = retrofit.create(UserService.class);
        UserAssignmentService userAssignmentService = retrofit.create(UserAssignmentService.class);
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
        InvoiceService invoiceService = retrofit.create(InvoiceService.class);
        InvoicePaymentService invoicePaymentService = retrofit.create(InvoicePaymentService.class);
        InvoiceMessagesService invoiceMessagesService = retrofit.create(InvoiceMessagesService.class);
        ExpenseCategoryService expenseCategoryService = retrofit.create(ExpenseCategoryService.class);
        ExpenseService expenseService = retrofit.create(ExpenseService.class);
        TeamReportService teamReportService = retrofit.create(TeamReportService.class);

        timesheetsApi = new TimesheetsApiImpl(timeEntryService);
        usersApi = new UsersApiImpl(userService);
        userAssignmentsApi = new UserAssignmentsApiImpl(userAssignmentService);
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
        invoicesApi = new InvoicesApiImpl(invoiceService);
        invoicePaymentsApi = new InvoicePaymentsApiImpl(invoicePaymentService);
        invoiceMessagesApi = new InvoiceMessagesApiImpl(invoiceMessagesService);
        expenseCategoriesApi = new ExpenseCategoriesApiImpl(expenseCategoryService);
        expensesApi = new ExpensesApiImpl(expenseService);
        teamReportsApi = new TeamReportApiImpl(teamReportService);

        log.debug("Harvest client initialized with: baseUrl=[{}], UA=[{}], accountID=[{}], rate limiting: {}r/{}s",
                baseUrl, userAgent, accountId, maxRequestWindow, windowSize);

    }

    private boolean getCompanyClockFormat(Retrofit retrofit) {
        CompanyApi tempCompanyApi = new CompanyApiImpl(retrofit.create(CompanyService.class));
        String clock = tempCompanyApi.get().getClock();
        return clock.equals("12h");
    }

    private InputStream openConfiguredStream(String configName) {
        String resourcePath = config.getString(configName);
        return Harvest.class.getResourceAsStream(resourcePath);
    }

    private Retrofit initRetrofit(Interceptor debugInterceptor, Interceptor authenticationInterceptor,
            Interceptor rateLimitInterceptor, boolean use12HoursFormat) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                .addNetworkInterceptor(rateLimitInterceptor)
                // debug interceptor goes last
                .addInterceptor(debugInterceptor)
                .build();

        // we need to start with something to be able to start retrofit and get the
        // right settings
        Gson gson = GsonConfiguration.getConfiguration(use12HoursFormat);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private Interceptor initAuthentication() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader("Authorization", "Bearer " + authToken);
                // optional, only needed for personal tokens
                if (!accountId.isEmpty()) {
                    ongoing.addHeader("Harvest-Account-id", accountId);
                }
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

    /**
     * @return the ZoneId of the currently authenticated User
     */
    public ZoneId getSelfTimezone() {
        User self = users().getSelf();
        return getTimezoneConfiguration().getZoneId(self.getTimezone()).orElseThrow(
                () -> new HarvestRuntimeException("invalid timezone for user " + self));
    }

    public TimesheetsApi timesheets() {
        return timesheetsApi;
    }

    public UsersApi users() {
        return usersApi;
    }

    public UserAssignmentsApi userAssignments() {
        return userAssignmentsApi;

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

    public InvoicesApi invoices() {
        return invoicesApi;
    }

    public InvoiceMessagesApi invoiceMessages() {
        return invoiceMessagesApi;
    }

    public InvoicePaymentsApi invoicePayments() {
        return invoicePaymentsApi;
    }

    public ExpenseCategoriesApi expenseCategories() {
        return expenseCategoriesApi;
    }

    public ExpensesApi expenses() {
        return expensesApi;
    }

    public TeamReportsApi teamReports() { return teamReportsApi; }

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
