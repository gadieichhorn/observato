package com.rds.observato.api.request;

import com.rds.observato.validation.Validator;

public record CreateAccountRequest(String name, String owner) {

  public CreateAccountRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrEmpty(owner, "owner");
  }
}
