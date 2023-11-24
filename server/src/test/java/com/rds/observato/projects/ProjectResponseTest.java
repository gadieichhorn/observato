package com.rds.observato.projects;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rds.observato.extentions.Mapper;
import org.junit.jupiter.api.Test;

class ProjectResponseTest {

  public static ObjectMapper mapper = Mapper.create();

  @Test
  void json() throws JsonProcessingException {
    String json = mapper.writeValueAsString(new GetProjectResponse(1l, 2l, 0, "A", "B"));
    assertThat(json).isNotEmpty();
    assertThat(mapper.readValue(json, GetProjectResponse.class))
        .isNotNull()
        .hasFieldOrPropertyWithValue("id", 1l);
  }
}
