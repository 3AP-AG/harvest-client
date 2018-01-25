package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;

public class InvalidAuthorizationException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(InvalidAuthorizationException.class);

    public InvalidAuthorizationException(ResponseBody responseBody) {
        super(responseBody, 403);
    }
}
