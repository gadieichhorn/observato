package com.rds.observato.api.model;

import com.rds.observato.api.Error;
import io.vavr.control.Either;

public interface Rule {

  Either<Error, Boolean> test(Task task, Agent agent);
}
