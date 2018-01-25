package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;

public class RateLimitedException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(RateLimitedException.class);

    public RateLimitedException(ResponseBody responseBody) {
        super(responseBody, 429);
    }
}
