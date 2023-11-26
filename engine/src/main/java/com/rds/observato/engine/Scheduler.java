package com.rds.observato.engine;

import com.rd.observato.api.*;
import com.rd.observato.api.Error;
import io.vavr.control.Either;
import java.util.Set;

public interface Scheduler {

  Either<Error, Assignment> valid(Policy policy, Task task, Schedule schedule);

  Either<Error, Set<Resource>> candidates(Policy policy, Task task, Schedule schedule);
}
