package com.rds.observato.engine;

import com.rd.observato.api.*;
import com.rd.observato.api.Error;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleScheduler implements Scheduler {
  @Override
  public Either<Error, Assignment> valid(Policy policy, Task task, Schedule schedule) {
    Set<Resource> collect =
        schedule.resources().stream()
            .filter(resource -> policy.rules().stream().anyMatch(rule -> rule.test(task, resource)))
            .collect(Collectors.toSet());

    return Either.left(new SchedulingError("no match"));
  }

  @Override
  public Either<Error, Set<Resource>> candidates(Policy policy, Task task, Schedule schedule) {
    return Try.of(
            () ->
                schedule.resources().stream()
                    .filter(
                        resource ->
                            policy.rules().stream().anyMatch(rule -> rule.test(task, resource)))
                    .collect(Collectors.toSet()))
        .toEither()
        .mapLeft(SchedulingError::from);
  }

  public Either<Error, Set<Availability>> availability(
      Task task, Resource candidate, Schedule schedule) {

    //    schedule.assignments().stream().filter(assignment ->
    // assignment.resource().equals(candidate)).map(assignment -> schedule.rules().stream().ma)

    return Either.left(new SchedulingError("no availability"));
  }
}
