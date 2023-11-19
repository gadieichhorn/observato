package com.rds.observato.projects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectConverterTest {

  @Test
  void convert() {
    Assertions.assertThat(new ProjectConverter().convert(new ProjectView(1, 0, 2, "A", "B")))
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("account", 2L)
        .hasFieldOrPropertyWithValue("name", "A")
        .hasFieldOrPropertyWithValue("description", "B");
  }
}
