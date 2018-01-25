package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class InvalidAuthorizationException extends HarvestHttpException {

    private static final long serialVersionUID = -5524348538019108809L;

    public InvalidAuthorizationException(ResponseBody responseBody) {
        super(responseBody, 403);
    }
}
