package exception;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForbiddenException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(ForbiddenException.class);

    public ForbiddenException(ResponseBody responseBody) {
        super(responseBody, 401);
    }
}
