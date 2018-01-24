package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLimitedException extends HarvestRuntimeException {

    private static final Logger log = LoggerFactory.getLogger(RateLimitedException.class);
}
