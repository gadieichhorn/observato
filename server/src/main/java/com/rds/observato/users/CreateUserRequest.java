package com.rds.observato.users;

import com.rds.observato.validation.Validator;

public record CreateUserRequest(String name, String password) {

  public CreateUserRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrEmpty(password, "password");
  }
}
