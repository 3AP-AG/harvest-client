package ch.aaap.harvestclient;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.exception.*;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Testing Exception handling (bad http responses)
 */
@HarvestTest
class ExceptionsTest {

    private static final String ERROR_MESSAGE = "error test message";
    private static final String ERROR_JSON = "{ \"message\":\"" + ERROR_MESSAGE + "\"}";
    private MockWebServer server;

    @BeforeAll
    static void beforeAll() {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger(); // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    @BeforeEach
    void beforeEach() {
        server = new MockWebServer();
    }

    @AfterEach
    void afterEach() throws IOException {
        server.shutdown();
    }

    @ParameterizedTest(name = "http code {1} should throw {0}")
    @MethodSource("createCases")
    void testGeneric(Class<HarvestHttpException> exceptionClass, int code) {
        server.enqueue(new MockResponse().setBody(ERROR_JSON).setResponseCode(code));
        // Harvest init does one request for the company
        HarvestHttpException httpException = assertThrows(exceptionClass, this::startServer);
        assertThat(httpException.getHttpCode()).isEqualTo(code);
    }

    @Test
    void testRateLimit() {
        server.enqueue(new MockResponse().setBody("Rate limited!").setResponseCode(429).setHeader("Retry-After", "0"));
        server.enqueue(new MockResponse().setBody("Rate limited!").setResponseCode(429).setHeader("Retry-After", "0"));
        server.enqueue(new MockResponse().setBody("Rate limited!").setResponseCode(429).setHeader("Retry-After", "0"));
        server.enqueue(new MockResponse().setBody("Rate limited!").setResponseCode(429).setHeader("Retry-After", "0"));
        // Harvest init does one request for the company
        assertThrows(RateLimitedException.class, this::startServer);
    }

    private static Stream<Arguments> createCases() {
        return Stream.of(
                Arguments.of(InvalidAuthorizationException.class, 401),
                Arguments.of(ForbiddenException.class, 403),
                Arguments.of(NotFoundException.class, 404),
                Arguments.of(HarvestHttpException.class, 405),
                Arguments.of(ServerErrorException.class, 500),
                Arguments.of(HarvestHttpException.class, 501),
                Arguments.of(RequestProcessingException.class, 422));
    }

    private void startServer() throws IOException {
        server.start();
        HttpUrl baseUrl = server.url("");

        Config config = ConfigFactory.defaultReference();
        config = config.withValue("harvest.baseUrl", ConfigValueFactory.fromAnyRef(baseUrl.toString()));
        new Harvest(config);
    }

}
