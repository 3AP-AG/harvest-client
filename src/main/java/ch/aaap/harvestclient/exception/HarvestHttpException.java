package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class HarvestHttpException extends HarvestRuntimeException {

    private static final long serialVersionUID = -214892161508112441L;
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
