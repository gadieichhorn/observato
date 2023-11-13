package com.rds.observato.validation;

import com.rds.observato.error.Error;

public record ValidationError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new ValidationError(throwable.getMessage());
  }
}
