package com.rds.observato.engine;

import com.rd.observato.api.*;
import com.rd.observato.api.Error;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleScheduler implements Scheduler {

  @Override
  public Either<Error, Set<Resource>> candidates(Policy policy, Task task, Schedule schedule) {
    return Try.of(() -> candidates(policy.rules(), task, schedule.assignments().keySet()))
        .toEither()
        .mapLeft(SchedulingError::from);
  }

  private Set<Resource> candidates(Set<Rule> rules, Task task, Set<Resource> resources) {
    return resources.stream()
        .filter(resource -> rules.stream().anyMatch(rule -> rule.test(task, resource)))
        .collect(Collectors.toSet());
  }

  @Override
  public Map<Resource, Set<Availability>> availability(Schedule schedule) {
    return schedule.assignments().entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry ->
                    AvailabilityFinder.findGaps(
                        schedule.start(), schedule.end(), entry.getValue())));
  }
}
