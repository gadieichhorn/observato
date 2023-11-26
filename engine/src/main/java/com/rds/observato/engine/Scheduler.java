package com.rds.observato.engine;

import com.rd.observato.api.*;
import com.rd.observato.api.Error;
import io.vavr.control.Either;
import java.util.Map;
import java.util.Set;

public interface Scheduler {

  Either<Error, Set<Resource>> candidates(Policy policy, Task task, Schedule schedule);

  Map<Resource, Set<Availability>> availability(Schedule schedule);
}
