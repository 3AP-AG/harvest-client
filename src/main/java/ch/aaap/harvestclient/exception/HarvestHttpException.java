package ch.aaap.harvestclient.exception;

import com.google.gson.Gson;

import ch.aaap.harvestclient.core.gson.GsonConfiguration;
import okhttp3.ResponseBody;

public class HarvestHttpException extends HarvestRuntimeException {

    private static final long serialVersionUID = -214892161508112441L;
    private final ResponseBody responseBody;
    private final int httpCode;

    public HarvestHttpException(ResponseBody responseBody, int httpCode) {
        super(initMessage(responseBody));
        this.responseBody = responseBody;
        this.httpCode = httpCode;
    }

    public HarvestHttpException(ResponseBody responseBody, int httpCode, String message) {
        super(message + " [" + initMessage(responseBody) + "]");
        this.responseBody = responseBody;
        this.httpCode = httpCode;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public static String initMessage(ResponseBody responseBody) {
        try {

            Gson gson = GsonConfiguration.getConfiguration();

            RequestProcessingErrorMessage parsedMessage = gson.fromJson(responseBody.charStream(),
                    RequestProcessingErrorMessage.class);

            return parsedMessage.getMessage();
        } catch (Exception e) {
            return "Error message was not in JSON format";
        }
    }
}
