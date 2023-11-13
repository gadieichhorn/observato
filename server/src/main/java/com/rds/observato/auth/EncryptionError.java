package com.rds.observato.auth;

import com.rds.observato.error.Error;

public record EncryptionError(String message) implements Error {

  public static Error from(Throwable throwable) {
    return new EncryptionError(throwable.getMessage());
  }
}
