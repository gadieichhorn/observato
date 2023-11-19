package com.rds.observato.resources;

import com.rds.observato.validation.Validator;

public record CreateResourceRequest(String name) {

  public CreateResourceRequest {
    Validator.checkIsNullOrEmpty(name, "name");
  }
}
