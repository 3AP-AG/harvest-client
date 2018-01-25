package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class ServerErrorException extends HarvestHttpException {

    private static final long serialVersionUID = -4787247644368534774L;

    public ServerErrorException(ResponseBody responseBody, int httpCode) {
        super(responseBody, httpCode);
    }
}
