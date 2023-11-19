package com.rds.observato.extentions;

public record SqlError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new SqlError(throwable.getMessage());
  }
}
