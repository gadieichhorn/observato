package com.rds.observato.assignments;

import com.rds.observato.validation.Validator;
import java.time.Instant;

public record CreateAssignmentRequest(Long task, Long resource, Instant start, Instant end) {

  public CreateAssignmentRequest {
    //    Validator.checkIsNull(account, "account");
    Validator.checkIsNull(task, "task");
    Validator.checkIsNull(resource, "resource");
    Validator.checkIsNull(start, "start");
    Validator.checkIsNull(end, "end");
  }
}
