package com.rds.observato.model;

import static org.junit.jupiter.api.Assertions.*;

import com.rd.observato.api.Skill;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleSkillTest {

  @Test
  void name() {
    Skill skill = new SimpleSkill("S1", "S1");
    Assertions.assertThat(skill).isNotNull();
  }
}
