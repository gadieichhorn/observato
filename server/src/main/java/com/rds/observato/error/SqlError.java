package com.rds.observato.error;

public record SqlError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new SqlError(throwable.getMessage());
  }
}
