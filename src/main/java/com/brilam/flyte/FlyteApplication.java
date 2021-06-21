package com.brilam.flyte;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlyteApplication {
  // Minimum lay over time of 1 hour
  public static final long MINIMUM_LAYOVER_TIME_MILLIS = 3600000;
  // Maximum lay over overtime of 6 hours
  public static final long MAXIMUM_LAYOVER_TIME_MILLIS = 21600000;

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    SpringApplication.run(FlyteApplication.class, args);
  }
}
