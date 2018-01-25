package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;

public class NotFoundException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(NotFoundException.class);

    public NotFoundException(ResponseBody responseBody) {
        super(responseBody, 404);
    }
}
