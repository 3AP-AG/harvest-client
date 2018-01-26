package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class RateLimitedException extends HarvestHttpException {

    private static final long serialVersionUID = -6895167449655779372L;

    public RateLimitedException(ResponseBody responseBody) {
        super(responseBody, 429);
    }
}
