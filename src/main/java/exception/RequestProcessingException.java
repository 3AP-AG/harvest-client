package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import core.gson.GsonConfiguration;
import okhttp3.ResponseBody;

public class RequestProcessingException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(RequestProcessingException.class);

    public RequestProcessingException(ResponseBody responseBody) {
        super(responseBody, 422, initMessage(responseBody));

    }

    public static String initMessage(ResponseBody responseBody) {
        Gson gson = GsonConfiguration.getConfiguration();

        RequestProcessingErrorMessage parsedMessage = gson.fromJson(responseBody.charStream(),
                RequestProcessingErrorMessage.class);

        return parsedMessage.getMessage();
    }

}
