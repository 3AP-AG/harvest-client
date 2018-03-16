package ch.aaap.harvestclient.core.ratelimit;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
import okhttp3.Response;

public class RateLimitInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);
    /**
     * Stay a bit below the published limit
     */
    private static final int MAX_REQUEST_PER_WINDOW = 95;

    private static final int WINDOW_SECONDS = 15;

    private final WindowCounter counter = new WindowCounter(WINDOW_SECONDS);

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            counter.waitUntilBelow(MAX_REQUEST_PER_WINDOW);
            counter.mark();
        } catch (InterruptedException e) {
            log.error("Interrupted", e);
        }
        return chain.proceed(chain.request());
    }
}
