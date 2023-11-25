package com.rds.observato.engine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rd.observato.api.Resource;
import com.rd.observato.api.Schedule;
import com.rd.observato.api.Skill;
import com.rd.observato.api.Task;
import com.rds.observato.model.SimpleResource;
import com.rds.observato.model.SimpleSkill;
import com.rds.observato.model.SimpleTask;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleSchedulerTest {

  @Test
  void name() {
    Skill s1 = new SimpleSkill("S1", "S1");
    Skill s2 = new SimpleSkill("S2", "S2");
    Task t1 = new SimpleTask("T1", ImmutableMap.of(s1, 1, s2, 2));
    Resource r1 = new SimpleResource("R1", ImmutableMap.of(s1, 1));

    Schedule schedule =
        new SimpleSchedule(
            ImmutableSet.of(t1),
            ImmutableSet.of(r1),
            ImmutableSet.of(),
            ImmutableSet.of(new MatchSkillRule(MatchSkillType.EXACTLY)));

    Assertions.assertThatCode(
            () -> new SimpleScheduler().valid(new SimpleTask("", ImmutableMap.of(s1,1,s2,2)), schedule))
        .doesNotThrowAnyException();
  }
}
