package ch.aaap.harvestclient.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.exception.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public static <T> T callOrThrow(Call<T> call) {

        try {
            log.debug("Executing call {}", call);
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                log.debug("Success");
                return response.body();
            } else {
                int code = response.code();
                log.debug("Failure -> {}", code);
                ResponseBody errorBody = response.errorBody();
                switch (code) {
                    case 401:
                        throw new InvalidAuthorizationException(errorBody);
                    case 403:
                        throw new ForbiddenException(errorBody);
                    case 404:
                        throw new NotFoundException(errorBody);
                    case 422:
                        throw new RequestProcessingException(errorBody);
                    case 429:
                        throw new RateLimitedException(errorBody);
                    case 500:
                        throw new ServerErrorException(errorBody, 500);
                    default:
                        // ok responses do not get here (e.g. 200)
                        throw new HarvestHttpException(errorBody, code);
                }
            }
        } catch (IOException e) {
            throw new HarvestRuntimeException(e);
        }
    }
}
