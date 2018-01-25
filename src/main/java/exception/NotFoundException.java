package exception;

import okhttp3.ResponseBody;

public class NotFoundException extends HarvestHttpException {

    private static final long serialVersionUID = 6973762410264959139L;

    public NotFoundException(ResponseBody responseBody) {
        super(responseBody, 404);
    }
}
