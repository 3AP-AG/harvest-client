package exception;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestHttpException extends HarvestRuntimeException {

    private static final Logger log = LoggerFactory.getLogger(HarvestHttpException.class);
    private final ResponseBody responseBody;
    private final int httpCode;

    public HarvestHttpException(ResponseBody responseBody, int httpCode) {
        super();
        this.responseBody = responseBody;
        this.httpCode = httpCode;
    }

    public HarvestHttpException(ResponseBody responseBody, int httpCode, String message) {
        super(message);
        this.responseBody = responseBody;
        this.httpCode = httpCode;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
