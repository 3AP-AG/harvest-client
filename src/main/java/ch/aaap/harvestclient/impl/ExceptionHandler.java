package ch.aaap.harvestclient.impl;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aaap.harvestclient.exception.*;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    public static <T> T callOrThrow(Call<T> call) {

        try {
            log.debug("Executing call {}", call.request());
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                log.debug("Success");
                return response.body();
            } else {
                int code = response.code();
                log.warn("Failure -> {}", code);
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
                        return retryLater(call, response);
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

    private static <T> T retryLater(Call<T> failedCall, Response<T> failedResponse) {

        int maxWait = 30;
        int wait = 0;
        if (failedResponse.code() == 429) {
            int secondsToWait = parseRetryAfter(failedResponse);
            wait = Math.min(secondsToWait, maxWait);
        }

        try {
            log.warn("Waiting {} seconds to retry call {}", wait, failedCall.request());
            Thread.sleep(TimeUnit.SECONDS.toMillis(wait));
        } catch (InterruptedException e) {
            throw new HarvestRuntimeException(e);
        }

        Call<T> call = failedCall.clone();
        // TODO a server returning constant 429 will put us in a loop
        return callOrThrow(call);
    }

    /**
     * See https://tools.ietf.org/html/rfc2616#section-14.37 for the format
     * 
     * @param <T>
     * @param failedResponse
     * @return the number of seconds to wait until we can retry
     */
    private static <T> int parseRetryAfter(Response<T> failedResponse) {

        int wait = 1;
        // extract retry-after headers
        Headers headers = failedResponse.headers();
        String header = headers.get("Retry-After");

        if (header == null) {
            // we don't understand the header
            log.error("Missing Retry-After Header for {}", failedResponse);
            return wait;
        }
        try {
            // is it an int?
            return Integer.parseInt(header);
        } catch (NumberFormatException e) {
            try {
                ZonedDateTime date = ZonedDateTime.parse(header, DateTimeFormatter.RFC_1123_DATE_TIME);
                // we don't care for values bigger than Integer.MAX_VALUE
                log.debug("Retry-After is {} ", date);
                return (int) Duration.between(Instant.now(), date).getSeconds();

            } catch (DateTimeParseException e1) {
                log.error("Invalid Retry-After Header for {}", failedResponse);
                return wait;
            }
        }
    }

}
