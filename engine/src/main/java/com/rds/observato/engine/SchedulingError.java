package com.rds.observato.engine;

import com.rd.observato.api.Error;

public record SchedulingError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new SchedulingError(throwable.getMessage());
  }
}
