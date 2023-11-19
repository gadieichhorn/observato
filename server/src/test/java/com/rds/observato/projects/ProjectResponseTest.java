package com.rds.observato.projects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rds.observato.api.response.GetProjectResponse;
import com.rds.observato.extentions.Mapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectResponseTest {

  public static ObjectMapper mapper = Mapper.create();

  @Test
  void name() throws JsonProcessingException {
    String json = mapper.writeValueAsString(new GetProjectResponse(1l, 2l, "A", "B"));
    Assertions.assertThat(json).isNotEmpty();
  }

  @Test
  void deserialize() throws JsonProcessingException {
    String json = """
            {"id":1,"account":2,"name":"A","description":"B"}
            """;
    mapper.readValue(json, GetProjectResponse.class);
  }
}
