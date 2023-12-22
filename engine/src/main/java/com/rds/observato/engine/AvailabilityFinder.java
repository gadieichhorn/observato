package com.rds.observato.engine;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Availability;
import com.rds.observato.model.NoopAssignment;
import com.rds.observato.model.SimpleAvailability;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class AvailabilityFinder {

  public static Set<Availability> findGaps(Instant start, Instant end, Set<Assignment> events) {
    List<Assignment> collect =
        events.stream()
            .filter(assignment -> assignment.start().isBefore(end))
            .filter(assignment -> assignment.end().isAfter(start))
            .sorted(Comparator.comparing(Assignment::start))
            .collect(Collectors.toList());

    collect.addFirst(new NoopAssignment(start.minus(1, ChronoUnit.HOURS), start));
    collect.addLast(new NoopAssignment(end, end.plus(1, ChronoUnit.HOURS)));

    Assignment[] array = collect.toArray(Assignment[]::new);

    Set<Availability> results = new HashSet<>();
    // we are adding 2 elements to array so never empty!
    for (int i = 1; i < array.length; i++) {
      if (array[i - 1].end().isBefore(array[i].start())) {
        results.add(new SimpleAvailability(array[i - 1].end(), array[i].start()));
      }
    }

    return results;
  }
}
