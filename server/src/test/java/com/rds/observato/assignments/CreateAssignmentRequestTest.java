package com.rds.observato.assignments;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateAssignmentRequestTest {

  @Test
  void nulls() {
    Assertions.assertThatThrownBy(() -> new CreateAssignmentRequest(null, null, null, null))
        .isInstanceOf(ValidationException.class);
  }
}
