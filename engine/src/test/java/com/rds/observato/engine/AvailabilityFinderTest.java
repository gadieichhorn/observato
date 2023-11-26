package com.rds.observato.engine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rd.observato.api.*;
import com.rds.observato.model.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AvailabilityFinderTest {

  private static final Logger log = LoggerFactory.getLogger(AvailabilityFinderTest.class);

  @Test
  void gaps() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of());
    Resource r1 = new SimpleResource("R1", ImmutableMap.of());

    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-01T23:59:59Z");

    SimpleAssignment a1 =
        new SimpleAssignment(
            t1,
            r1,
            start.plus(1, ChronoUnit.HOURS),
            start.plus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.HOURS));

    SimpleAssignment a2 =
        new SimpleAssignment(
            t1, r1, a1.end().plus(1, ChronoUnit.HOURS), a1.end().plus(2, ChronoUnit.HOURS));
    SimpleAssignment a3 =
        new SimpleAssignment(
            t1, r1, a2.end().plus(1, ChronoUnit.HOURS), a2.end().plus(2, ChronoUnit.HOURS));

    Assertions.assertThat(AvailabilityFinder.findGaps(start, end, ImmutableSet.of(a1, a2, a3)))
        .containsExactlyInAnyOrder(
            new SimpleAvailability(start, a1.start()),
            new SimpleAvailability(a1.end(), a2.start()),
            new SimpleAvailability(a2.end(), a3.start()),
            new SimpleAvailability(a3.end(), end));
  }

  @Test
  void noGapsBetweenAssignment() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of());
    Resource r1 = new SimpleResource("R1", ImmutableMap.of());

    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-01T23:59:59Z");

    SimpleAssignment a1 =
        new SimpleAssignment(
            t1,
            r1,
            start.plus(1, ChronoUnit.HOURS),
            start.plus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.HOURS));

    SimpleAssignment a2 =
        new SimpleAssignment(t1, r1, a1.end(), a1.end().plus(1, ChronoUnit.HOURS));
    SimpleAssignment a3 =
        new SimpleAssignment(t1, r1, a2.end(), a2.end().plus(1, ChronoUnit.HOURS));

    Assertions.assertThat(AvailabilityFinder.findGaps(start, end, ImmutableSet.of(a1, a2, a3)))
        .containsExactlyInAnyOrder(
            new SimpleAvailability(start, a1.start()), new SimpleAvailability(a3.end(), end));
  }

  @Test
  void overlapAssignment() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of());
    Resource r1 = new SimpleResource("R1", ImmutableMap.of());

    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-01T23:59:59Z");

    SimpleAssignment a1 =
        new SimpleAssignment(
            t1,
            r1,
            start.plus(1, ChronoUnit.HOURS),
            start.plus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.HOURS));

    SimpleAssignment a2 =
        new SimpleAssignment(
            t1, r1, a1.end().minus(5, ChronoUnit.MINUTES), a1.end().plus(1, ChronoUnit.HOURS));

    SimpleAssignment a3 =
        new SimpleAssignment(
            t1, r1, a2.end().minus(5, ChronoUnit.MINUTES), a2.end().plus(1, ChronoUnit.HOURS));

    Assertions.assertThat(AvailabilityFinder.findGaps(start, end, ImmutableSet.of(a1, a2, a3)))
        .containsExactlyInAnyOrder(
            new SimpleAvailability(start, a1.start()), new SimpleAvailability(a3.end(), end));
  }

  @Test
  void outside() {
    Task t1 = new SimpleTask("T1", Duration.ofHours(1), ImmutableMap.of());
    Resource r1 = new SimpleResource("R1", ImmutableMap.of());

    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-02T00:00:00Z");

    SimpleAssignment a1 =
        new SimpleAssignment(
            t1,
            r1,
            start.plus(1, ChronoUnit.HOURS),
            start.plus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.HOURS));

    SimpleAssignment a2 =
        new SimpleAssignment(
            t1, r1, start.minus(10, ChronoUnit.HOURS), start.minus(9, ChronoUnit.HOURS));

    SimpleAssignment a3 =
        new SimpleAssignment(t1, r1, end.plus(1, ChronoUnit.HOURS), end.plus(2, ChronoUnit.HOURS));

    Assertions.assertThat(AvailabilityFinder.findGaps(start, end, ImmutableSet.of(a1, a2, a3)))
        .containsExactlyInAnyOrder(
            new SimpleAvailability(start, a1.start()), new SimpleAvailability(a1.end(), end));
  }

  @Test
  void empty() {
    final Instant start = Instant.parse("2020-01-01T00:00:00Z");
    final Instant end = Instant.parse("2020-10-01T23:59:59Z");

    Assertions.assertThat(AvailabilityFinder.findGaps(start, end, ImmutableSet.of()))
        .containsExactly(new SimpleAvailability(start, end));
  }
}
