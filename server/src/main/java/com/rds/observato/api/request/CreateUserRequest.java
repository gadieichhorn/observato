package com.rds.observato.api.request;

import com.rds.observato.validation.Validator;

public record CreateUserRequest(String name, String password) {

  public CreateUserRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrEmpty(password, "password");
  }
}
