package com.rds.observato.api.request;

import com.rds.observato.validation.Validator;

public record CreateAccountRequest(String name, long owner) {

  public CreateAccountRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNull(owner, "owner");
  }
}
