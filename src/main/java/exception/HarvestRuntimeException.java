package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestRuntimeException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(HarvestRuntimeException.class);

    public HarvestRuntimeException() {
    }

    public HarvestRuntimeException(String message) {
        super(message);
    }

    public HarvestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HarvestRuntimeException(Throwable cause) {
        super(cause);
    }

}
