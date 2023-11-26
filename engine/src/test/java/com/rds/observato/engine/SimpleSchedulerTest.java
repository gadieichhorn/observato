package com.rds.observato.engine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rd.observato.api.*;
import com.rds.observato.engine.rules.MatchSkillRule;
import com.rds.observato.engine.rules.MatchSkillType;
import com.rds.observato.model.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SimpleSchedulerTest {
  private static final Logger log = LoggerFactory.getLogger(SimpleSchedulerTest.class);
  Skill s1 = new SimpleSkill("S1", "S1");
  Skill s2 = new SimpleSkill("S2", "S2");
  Skill s3 = new SimpleSkill("S3", "S3");

  Policy policy = new SimplePolicy(ImmutableSet.of(new MatchSkillRule(MatchSkillType.AT_LEAST)));

  @Test
  void candidates() {

    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of(s1, 1, s2, 2));

    Resource r1 = new SimpleResource("R1", ImmutableMap.of(s1, 3));
    Resource r2 = new SimpleResource("R2", ImmutableMap.of(s1, 2, s2, 2));
    Resource r3 = new SimpleResource("R3", ImmutableMap.of(s1, 2, s2, 3, s3, 5));

    Schedule schedule =
        new SimpleSchedule(
            Instant.parse("2020-01-01T00:00:00Z"),
            Instant.parse("2020-10-01T23:59:59Z"),
            ImmutableSet.of(t1),
            ImmutableMap.of(r1, ImmutableSet.of(), r2, ImmutableSet.of(), r3, ImmutableSet.of()));

    Assertions.assertThat(
            new SimpleScheduler()
                .candidates(
                    policy,
                    new SimpleTask("", Duration.ofHours(2), ImmutableMap.of(s1, 1, s2, 2)),
                    schedule))
        .containsExactlyInAnyOrder(ImmutableSet.of(r2, r3));
  }

  @Test
  void availability() {

    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of(s1, 1, s2, 2));
    Task t2 = new SimpleTask("T2", Duration.ofHours(1), ImmutableMap.of(s1, 1, s2, 2));
    Task t3 = new SimpleTask("T3", Duration.ofHours(1), ImmutableMap.of(s1, 1, s2, 2));

    Resource r1 = new SimpleResource("R1", ImmutableMap.of(s1, 3));
    Resource r2 = new SimpleResource("R2", ImmutableMap.of(s2, 6));
    Resource r3 = new SimpleResource("R3", ImmutableMap.of(s1, 1, s2, 3));

    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-02T00:00:00Z");

    Assignment a1 =
        new SimpleAssignment(
            t1, r1, start.plus(1, ChronoUnit.HOURS), start.plus(2, ChronoUnit.HOURS));

    Assignment a2 =
        new SimpleAssignment(
            t2, r2, start.plus(2, ChronoUnit.HOURS), start.plus(3, ChronoUnit.HOURS));

    Assignment a3 =
        new SimpleAssignment(
            t3, r3, start.plus(4, ChronoUnit.HOURS), start.plus(6, ChronoUnit.HOURS));

    Schedule schedule =
        new SimpleSchedule(
            start,
            end,
            ImmutableSet.of(t1),
            ImmutableMap.of(
                r1, ImmutableSet.of(a1), r2, ImmutableSet.of(a2), r3, ImmutableSet.of(a3)));

    Assertions.assertThat(new SimpleScheduler().availability(schedule))
        .containsExactlyInAnyOrderEntriesOf(
            ImmutableMap.of(
                r1,
                ImmutableSet.of(
                    new SimpleAvailability(start, a1.start()),
                    new SimpleAvailability(a1.end(), end)),
                r2,
                ImmutableSet.of(
                    new SimpleAvailability(start, a2.start()),
                    new SimpleAvailability(a2.end(), end)),
                r3,
                ImmutableSet.of(
                    new SimpleAvailability(start, a3.start()),
                    new SimpleAvailability(a3.end(), end))));
  }
}
