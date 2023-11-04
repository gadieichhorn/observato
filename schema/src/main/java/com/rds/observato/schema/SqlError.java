package com.rds.observato.schema;

import com.rds.observato.api.Error;

public record SqlError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new SqlError(throwable.getMessage());
  }
}
