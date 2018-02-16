package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class RequestProcessingException extends HarvestHttpException {

    private static final long serialVersionUID = -3464804468055061178L;

    public RequestProcessingException(ResponseBody responseBody) {
        super(responseBody, 422);
    }

}
