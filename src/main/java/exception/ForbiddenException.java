package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;

public class ForbiddenException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(ForbiddenException.class);

    public ForbiddenException(ResponseBody responseBody) {
        super(responseBody, 401);
    }
}
