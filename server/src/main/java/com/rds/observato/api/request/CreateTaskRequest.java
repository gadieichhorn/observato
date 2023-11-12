package com.rds.observato.api.request;

import com.rds.observato.validation.Validator;

public record CreateTaskRequest(String name, String description) {

  public CreateTaskRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrEmpty(description, "description");
  }
}
