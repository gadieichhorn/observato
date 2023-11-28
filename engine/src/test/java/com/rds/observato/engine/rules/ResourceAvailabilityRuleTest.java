package com.rds.observato.engine.rules;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableMap;
import com.rd.observato.api.*;
import com.rds.observato.model.SimpleAvailability;
import com.rds.observato.model.SimpleTask;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class ResourceAvailabilityRuleTest {
  private final Rule rule = new ResourceAvailabilityRule();

  private static final Clock CLOCK =
      Clock.fixed(Instant.parse("2016-08-31T00:00:00Z"), ZoneOffset.UTC);

  @Test
  void larger() {
    Task t1 = new SimpleTask("T1", Duration.ofMinutes(30), ImmutableMap.of());
    Availability a1 =
        new SimpleAvailability(Instant.now(CLOCK), Instant.now(CLOCK).plus(1, ChronoUnit.HOURS));
    assertThat(rule.test(t1, a1)).isTrue();
  }

  @Test
  void smaller() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(2), ImmutableMap.of());
    Availability a1 =
            new SimpleAvailability(Instant.now(CLOCK), Instant.now(CLOCK).plus(1, ChronoUnit.HOURS));
    assertThat(rule.test(t1, a1)).isFalse();
  }

  @Test
  void same() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(2), ImmutableMap.of());
    Availability a1 =
            new SimpleAvailability(Instant.now(CLOCK), Instant.now(CLOCK).plus(2, ChronoUnit.HOURS));
    assertThat(rule.test(t1, a1)).isTrue();
  }
}
