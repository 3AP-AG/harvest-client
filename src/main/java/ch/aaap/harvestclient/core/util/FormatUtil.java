package ch.aaap.harvestclient.core.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Contains utility methods to get formatters based on user input
 */
public class FormatUtil {

  /**
   * Gets a time formatter according to whether the company uses the 12 hours harvest format or not
   * @param use12HoursFormat if the 12 hours format should be used or not
   * @return a @{@link DateTimeFormatter} instance
   */
  public static DateTimeFormatter getDateTimeFormatter(boolean use12HoursFormat) {

    String pattern = "H:mm";
    if (use12HoursFormat) {
      pattern = "h:mma";
    }

    return new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(pattern).toFormatter(
        Locale.ENGLISH);
  }
}

