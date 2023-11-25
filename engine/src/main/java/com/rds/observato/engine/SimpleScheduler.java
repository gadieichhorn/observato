package com.rds.observato.engine;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Error;
import com.rd.observato.api.Schedule;
import com.rd.observato.api.Task;
import com.rds.observato.model.SimpleAssignment;
import io.vavr.control.Either;

public class SimpleScheduler implements Scheduler {
  @Override
  public Either<Error, Assignment> valid(Task task, Schedule schedule) {

    schedule
        .resources()
        .forEach(resource -> schedule.rules().forEach(rule -> rule.test(task, resource)));

    return Either.right(new SimpleAssignment());
  }
}
