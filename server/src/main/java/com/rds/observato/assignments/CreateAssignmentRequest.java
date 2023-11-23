package com.rds.observato.assignments;

import com.rds.observato.validation.Validator;
import java.time.Instant;

public record CreateAssignmentRequest(Long task, Long resource, Instant start, Instant end) {

  public CreateAssignmentRequest {
    //    Validator.checkIsNull(account, "account");
    Validator.checkIsNullOrNegative(task, "task");
    Validator.checkIsNullOrNegative(resource, "resource");
    Validator.checkIsNull(start, "start");
    Validator.checkIsNull(end, "end");
  }
}
