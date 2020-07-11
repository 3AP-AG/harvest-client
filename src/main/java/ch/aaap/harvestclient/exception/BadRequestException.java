package ch.aaap.harvestclient.exception;

import okhttp3.ResponseBody;

public class BadRequestException extends HarvestHttpException {

  public BadRequestException(ResponseBody responseBody) {
    super(responseBody, 400);
  }
}
