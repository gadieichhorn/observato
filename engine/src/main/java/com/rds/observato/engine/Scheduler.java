package com.rds.observato.engine;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Error;
import com.rd.observato.api.Schedule;
import com.rd.observato.api.Task;
import io.vavr.control.Either;

public interface Scheduler {

  Either<Error, Assignment> valid(Task task, Schedule schedule);
}
