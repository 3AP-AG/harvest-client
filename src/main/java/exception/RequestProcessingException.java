package exception;

import com.google.gson.Gson;

import core.gson.GsonConfiguration;
import okhttp3.ResponseBody;

public class RequestProcessingException extends HarvestHttpException {

    private static final long serialVersionUID = -3464804468055061178L;

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
