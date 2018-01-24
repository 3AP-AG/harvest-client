package impl;

import exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public static <T> T callOrThrow(Call<T> call) {

        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                int code = response.code();
                switch (code) {
                    case 401:
                        throw new InvalidAuthorizationException();
                    case 403:
                        throw new ForbiddenException();
                    case 404:
                        throw new NotFoundException();
                    case 422:
                        throw new RequestProcessingException(response.errorBody());
                    case 429:
                        throw new RateLimitedException();
                    case 500:
                        throw new ServerErrorException();
                    default:
                        // ok responses do not get here (e.g. 200)
                        throw new HarvestRuntimeException(code);
                }
            }
        } catch (IOException e) {
            throw new HarvestRuntimeException(e);
        }
    }
}
