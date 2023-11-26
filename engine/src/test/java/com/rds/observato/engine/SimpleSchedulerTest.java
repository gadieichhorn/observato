package com.rds.observato.engine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rd.observato.api.*;
import com.rds.observato.engine.rules.MatchSkillRule;
import com.rds.observato.engine.rules.MatchSkillType;
import com.rds.observato.model.*;
import java.time.Duration;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleSchedulerTest {

  Skill s1 = new SimpleSkill("S1", "S1");
  Skill s2 = new SimpleSkill("S2", "S2");
  Skill s3 = new SimpleSkill("S3", "S3");

  Policy policy = new SimplePolicy(ImmutableSet.of(new MatchSkillRule(MatchSkillType.AT_LEAST)));

  @Test
  void candidates() {

    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of(s1, 1, s2, 2));

    Resource r1 = new SimpleResource("R1", ImmutableMap.of(s1, 3));
    Resource r2 = new SimpleResource("R2", ImmutableMap.of(s2, 6));
    Resource r3 = new SimpleResource("R3", ImmutableMap.of(s1, 1, s2, 3));

    SimpleAssignment a1 =
        new SimpleAssignment(
            t1, r1, Instant.parse("2020-01-01T10:00:00Z"), Instant.parse("2020-01-01T12:00:00Z"));

    Schedule schedule =
        new SimpleSchedule(
            Instant.parse("2020-01-01T00:00:00Z"),
            Instant.parse("2020-10-01T23:59:59Z"),
            ImmutableSet.of(t1),
            ImmutableSet.of(r1, r2, r3),
            ImmutableSet.of(a1));

    Assertions.assertThat(
            new SimpleScheduler()
                .candidates(
                    policy,
                    new SimpleTask("", Duration.ofHours(2), ImmutableMap.of(s1, 1, s2, 2)),
                    schedule))
        .containsExactly(ImmutableSet.of(r3));
  }
}
