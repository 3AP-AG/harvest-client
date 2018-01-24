package exception;

import com.google.gson.Gson;
import core.gson.GsonConfiguration;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestProcessingException extends HarvestHttpException {


    private static final Logger log = LoggerFactory.getLogger(RequestProcessingException.class);

    public static String initMessage(ResponseBody responseBody) {
        Gson gson = GsonConfiguration.getConfiguration();

        RequestProcessingErrorMessage parsedMessage = gson.fromJson(responseBody.charStream(), RequestProcessingErrorMessage.class);

        return parsedMessage.getMessage();
    }

    public RequestProcessingException(ResponseBody responseBody) {
        super(responseBody, 422, initMessage(responseBody));

    }

}
