package com.rds.observato.tasks;

import com.rds.observato.validation.Validator;

public record UpdateTaskRequest(long id, int revision, String name, String description) {

  public UpdateTaskRequest {
    Validator.checkIsNullOrNegative(id, "id");
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrNotNegative(revision, "revision");
    Validator.checkIsNullOrEmpty(description, "description");
  }
}
