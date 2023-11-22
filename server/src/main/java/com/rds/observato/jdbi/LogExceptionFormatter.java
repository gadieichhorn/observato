package com.rds.observato.jdbi;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogExceptionFormatter {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogExceptionFormatter.class);

  public static String formatErrorMessage(long id) {
    return String.format(
        "There was an error processing your request. It has been logged (ID %016x).", id);
  }

  public static long logException(final Throwable exception) {
    long id = ThreadLocalRandom.current().nextLong();
    if (exception instanceof SQLException) {
      for (Throwable ex : (SQLException) exception) {
        logException(id, ex);
      }
    } else {
      logException(id, exception);
    }
    return id;
  }

  private static void logException(final long id, final Throwable exception) {
    LOGGER.error(formatLogMessage(id), exception);
  }

  private static String formatLogMessage(final long id) {
    return String.format("Error handling a request: %016x", id);
  }
}
