package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class ForbiddenException extends HarvestHttpException {

    private static final long serialVersionUID = 2595592240939332228L;

    public ForbiddenException(ResponseBody responseBody) {
        super(responseBody, 403);
    }
}
