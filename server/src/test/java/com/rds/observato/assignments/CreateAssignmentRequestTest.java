package com.rds.observato.assignments;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rds.observato.extentions.Mapper;
import com.rds.observato.validation.ValidationException;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateAssignmentRequestTest {

  @Test
  void invalid() {
    Assertions.assertThatThrownBy(() -> new CreateAssignmentRequest(null, null, null, null))
        .isInstanceOf(ValidationException.class);
    Assertions.assertThatThrownBy(
            () -> new CreateAssignmentRequest(0L, 1L, Instant.now(), Instant.now()))
        .isInstanceOf(ValidationException.class);
    Assertions.assertThatThrownBy(
            () -> new CreateAssignmentRequest(1L, 0L, Instant.now(), Instant.now()))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void valid() {
    Assertions.assertThatCode(
            () -> new CreateAssignmentRequest(1L, 2L, Instant.now(), Instant.now()))
        .doesNotThrowAnyException();
  }

  @Test
  void serialized() {
    ObjectMapper mapper = Mapper.create();
    Assertions.assertThatCode(
            () ->
                mapper.writeValueAsString(
                    new CreateAssignmentRequest(1L, 2L, Instant.now(), Instant.now())))
        .doesNotThrowAnyException();
  }

  @Test
  void deserialized() throws JsonProcessingException {
    String json =
        """
    {
      "task":1,
      "resource":2,
      "start":"2023-11-23T09:37:48Z",
      "end":"2023-11-23T09:37:48Z"
    }
    """;
    ObjectMapper mapper = Mapper.create();
    Assertions.assertThat(mapper.readValue(json, CreateAssignmentRequest.class))
        .hasFieldOrPropertyWithValue("task", 1L)
        .hasFieldOrPropertyWithValue("resource", 2L)
        .hasFieldOrPropertyWithValue("start", Instant.parse("2023-11-23T09:37:48Z"))
        .hasFieldOrPropertyWithValue("end", Instant.parse("2023-11-23T09:37:48Z"));
  }
}
