package ch.aaap.harvestclient.core.ratelimit;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
import okhttp3.Response;

public class RateLimitInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);

    private final WindowCounter counter;
    private final int maxRequestPerWindow;

    private final int windowSeconds;

    public RateLimitInterceptor(int maxRequestPerWindow, int windowSeconds) {

        this.maxRequestPerWindow = maxRequestPerWindow;
        this.windowSeconds = windowSeconds;
        counter = new WindowCounter(windowSeconds);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            counter.waitUntilBelow(maxRequestPerWindow);
            counter.mark();
        } catch (InterruptedException e) {
            log.error("Interrupted", e);
        }
        return chain.proceed(chain.request());
    }

    public int getMaxRequestPerWindow() {
        return maxRequestPerWindow;
    }

    public int getWindowSeconds() {
        return windowSeconds;
    }
}
