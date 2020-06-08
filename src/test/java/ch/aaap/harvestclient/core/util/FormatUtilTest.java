package ch.aaap.harvestclient.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import org.junit.jupiter.api.Test;

class FormatUtilTest {


  @Test
  public void shouldParseLowercaseCorrectly() {

    String timeString = "12:01pm";
    DateTimeFormatter formatter = FormatUtil.getDateTimeFormatter(true);
    TemporalAccessor time = LocalTime.parse(timeString, formatter);

    assertEquals(12, time.get(ChronoField.HOUR_OF_DAY));
  }

  @Test
  public void shouldParseUppercaseCorrectly() {

    String timeString = "12:01PM";
    DateTimeFormatter formatter = FormatUtil.getDateTimeFormatter(true);
    TemporalAccessor time = LocalTime.parse(timeString, formatter);

    assertEquals(12, time.get(ChronoField.HOUR_OF_DAY));
  }

  @Test
  public void shouldParseWithoutCorrectly() {

    String timeString = "12:01";
    DateTimeFormatter formatter = FormatUtil.getDateTimeFormatter(false);
    TemporalAccessor time = LocalTime.parse(timeString, formatter);

    assertEquals(12, time.get(ChronoField.HOUR_OF_DAY));
  }

}