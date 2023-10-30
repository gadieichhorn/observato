package com.rds.observato.core;

import com.rds.observato.api.Error;
import com.rds.observato.api.model.Agent;
import com.rds.observato.api.model.Rule;
import com.rds.observato.api.model.Task;
import io.vavr.control.Either;

public record MatchSkillRule() implements Rule {

  @Override
  public Either<Error, Boolean> test(Task task, Agent agent) {
//    task.skills().forEach((s,l)-> agent.skills().computeIfPresent(s, (ss,ll) -> {
//      if(l > ll) return false;
//        return ll;
//    });
    return Either.right(agent.skills().keySet().containsAll(task.skills().keySet()));
  }
}
