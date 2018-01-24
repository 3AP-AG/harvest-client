package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestProcessingErrorMessage {

    private static final Logger log = LoggerFactory.getLogger(RequestProcessingErrorMessage.class);

    private String message;

    public String getMessage() {
        return message;
    }
}
