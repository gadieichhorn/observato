package com.rds.observato.core;

import com.rds.observato.api.Error;
import com.rds.observato.api.model.Agent;
import com.rds.observato.api.model.Rule;
import com.rds.observato.api.model.Task;
import io.vavr.control.Either;

public record MatchSkillRule() implements Rule {

  Either<Error, Boolean> test(Task task, Agent agent) {
    //    Try.of(() -> task.skills().forEach((skill, level) -> {
    //      boolean b = agent.skills().containsKey(skill);
    //    })).toEither();
    return Either.right(false);
  }
}
