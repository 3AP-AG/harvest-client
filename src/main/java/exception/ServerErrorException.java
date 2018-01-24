package exception;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerErrorException extends HarvestHttpException {

    private static final Logger log = LoggerFactory.getLogger(ServerErrorException.class);

    public ServerErrorException(ResponseBody responseBody, int httpCode) {
        super(responseBody, httpCode);
    }
}
