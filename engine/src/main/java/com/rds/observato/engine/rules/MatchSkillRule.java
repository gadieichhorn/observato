package com.rds.observato.engine.rules;

import com.rd.observato.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record MatchSkillRule(MatchSkillType type) implements Rule {

  private static final Logger log = LoggerFactory.getLogger(MatchSkillRule.class);

  @Override
  public boolean test(Task task, Resource resource) {
    return type.matcher.match(task.skills(), resource.skills());
  }

  @Override
  public boolean test(Task task, Availability availability) {
    return true;
  }

  @Override
  public boolean accept(RuleVisitor visitor) {
    return visitor.visit(this);
  }
}
