package exception;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLimitedException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(RateLimitedException.class);

    public RateLimitedException(ResponseBody responseBody) {
        super(responseBody, 429);
    }
}
