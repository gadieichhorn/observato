package com.rds.observato.api.service;

import com.rds.observato.api.Error;
import com.rds.observato.api.model.Agent;
import com.rds.observato.api.model.Assignment;
import com.rds.observato.api.model.Task;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Set;

public interface Scheduler {

  Either<Error, Assignment> schedule(Task task, Agent agent, Instant start);

  Either<Error, Assignment> schedule(Task task, Set<Agent> agents, Instant start);
}
