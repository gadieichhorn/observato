package com.rds.observato.projects;

import com.rds.observato.validation.Validator;

public record CreateProjectRequest(String name, String description) {

  public CreateProjectRequest {
    Validator.checkIsNullOrEmpty(name, "name");
    Validator.checkIsNullOrEmpty(description, "description");
  }
}
